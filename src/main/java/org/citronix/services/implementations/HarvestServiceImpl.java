package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.models.Harvest;
import org.citronix.repositories.HarvestRepository;
import org.citronix.services.HarvestService;
import org.citronix.utils.mappers.HarvestMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {
    private final HarvestRepository repository;
    private final HarvestMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public JpaRepository<Harvest, UUID> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<Harvest, HarvestRequestDTO, HarvestResponseDTO> getMapper() {
        return mapper;
    }

    @Override
    public void startHarvest(String id) {
        eventPublisher.publishEvent(new HarvestStartedEvent(this, id));
    }
}
