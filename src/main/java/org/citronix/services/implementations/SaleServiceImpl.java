package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import org.citronix.dtos.request.SaleRequestDTO;
import org.citronix.dtos.response.RevenueDTO;
import org.citronix.dtos.response.SaleResponseDTO;
import org.citronix.events.RevenueCalcStartedEvent;
import org.citronix.events.SaleSaveEvent;
import org.citronix.models.Client;
import org.citronix.models.Sale;
import org.citronix.repositories.ClientRepository;
import org.citronix.repositories.SaleRepository;
import org.citronix.services.SaleService;
import org.citronix.utils.mappers.GenericMapper;
import org.citronix.utils.mappers.SaleMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Transactional
@Service
public class SaleServiceImpl extends GenericServiceImpl<Sale, SaleRequestDTO, SaleResponseDTO> implements SaleService {
    private final SaleRepository repository;
    private final ClientRepository clientRepository;
    private final SaleMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public SaleServiceImpl(SaleRepository repository, ClientRepository clientRepository, SaleMapper mapper, ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public SaleResponseDTO save(SaleRequestDTO req) {
        Sale entity = mapper.toEntity(req);
        validateSaleConstraints(entity, req.harvestId());
        entity.setClient(createOrFetchClient(entity.getClient()));
        repository.save(entity);
        return findById(entity.getId().toString());
    }

    public Client createOrFetchClient(Client client) {
        return clientRepository.findById(client.getCin())
                .orElseGet(() -> clientRepository.save(client));
    }

    public void validateSaleConstraints(Sale sale, String harvestId) {
        SaleSaveEvent event = new SaleSaveEvent(this, harvestId);
        eventPublisher.publishEvent(event);

        try {
            double totalQuantity = event.getResult().get();
            double soldQuantity = getTotalSoldQuantity(getSalesByHarvestId(harvestId));

            if(soldQuantity > totalQuantity - sale.getQuantity()) {
                throw new IllegalArgumentException("Wanted quantity exceeds available stock.");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public double getTotalSoldQuantity(List<Sale> sales) {
        return sales.stream().mapToDouble(Sale::getQuantity).sum();
    }

    public double getTotalRevenue(List<Sale> sales) {
        return sales.stream().mapToDouble(s -> s.getQuantity() * s.getUnitPrice()).sum();
    }

    public List<Sale> getSalesByHarvestId(String harvestId) {
        return executeWithUUID(harvestId, repository::findByHarvestId);
    }

    @EventListener
    public void handleRevenueCalcStartedEvent(RevenueCalcStartedEvent revenueCalcStartedEvent) {
        String farmId = revenueCalcStartedEvent.getFarmId();
        List<Sale> sales = repository.findByFarmId(UUID.fromString(farmId));
        double totalRevenue = getTotalRevenue(sales);
        RevenueDTO revenueDTO = RevenueDTO.builder()
                .totalRevenue(totalRevenue)
                .sales(mapper.toDTOs(sales))
                .build();

        if(revenueDTO != null) revenueCalcStartedEvent.getResult().complete(revenueDTO);
    }
}
