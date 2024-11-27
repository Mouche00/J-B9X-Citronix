package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.dtos.response.RevenueDTO;
import org.citronix.events.FieldSaveEvent;
import org.citronix.events.RevenueCalcStartedEvent;
import org.citronix.models.Farm;
import org.citronix.repositories.FarmRepository;
import org.citronix.services.FarmService;
import org.citronix.services.GenericService;
import org.citronix.utils.mappers.FarmMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.citronix.repositories.FarmRepository.Specs.withCriteria;

@Transactional
@Service
public class FarmServiceImpl extends GenericServiceImpl<Farm, FarmRequestDTO, FarmResponseDTO> implements FarmService {
    private final FarmRepository repository;
    private final FarmMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public FarmServiceImpl(FarmRepository repository, FarmMapper mapper, ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void handleFieldSaveEvent(FieldSaveEvent fieldSaveEvent) {
        Farm farm = findEntityById(fieldSaveEvent.getFarmId());
        if(farm != null) fieldSaveEvent.getResult().complete(farm);
    }

    @Override
    public Page<FarmResponseDTO> searchFarms(String name, String location, Pageable pageable) {
        Page<Farm> farms = repository.findAll(withCriteria(name, location), pageable);
        return farms.map(mapper::toDTO);
    }

    @Override
    public RevenueDTO calculateRevenue(String farmId) {
        RevenueCalcStartedEvent event = new RevenueCalcStartedEvent(this, farmId);
        eventPublisher.publishEvent(event);

        try {
            return event.getResult().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
