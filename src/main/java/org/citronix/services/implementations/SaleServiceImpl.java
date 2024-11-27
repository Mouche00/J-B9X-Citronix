package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import org.citronix.dtos.request.SaleRequestDTO;
import org.citronix.dtos.response.SaleResponseDTO;
import org.citronix.models.Client;
import org.citronix.models.Sale;
import org.citronix.repositories.ClientRepository;
import org.citronix.repositories.SaleRepository;
import org.citronix.services.SaleService;
import org.citronix.utils.mappers.GenericMapper;
import org.citronix.utils.mappers.SaleMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
public class SaleServiceImpl extends GenericServiceImpl<Sale, SaleRequestDTO, SaleResponseDTO> implements SaleService {
    private final SaleRepository repository;
    private final ClientRepository clientRepository;
    private final SaleMapper mapper;
    public SaleServiceImpl(SaleRepository repository, ClientRepository clientRepository, SaleMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    @Override
    public SaleResponseDTO save(SaleRequestDTO req) {
        Sale entity = mapper.toEntity(req);
        entity.setClient(createOrFetchClient(entity.getClient()));
        repository.save(entity);
        return findById(entity.getId().toString());
    }

    public Client createOrFetchClient(Client client) {
        return clientRepository.findById(client.getCin())
                .orElseGet(() -> clientRepository.save(client));
    }
}
