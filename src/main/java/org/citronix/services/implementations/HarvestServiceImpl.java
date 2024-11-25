package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;
import org.citronix.repositories.HarvestRepository;
import org.citronix.services.HarvestService;
import org.citronix.utils.mappers.HarvestMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
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
    public List<HarvestDetailResponseDTO> startHarvest(String id) {
        CompletableFuture<List<HarvestDetailResponseDTO>> result = new CompletableFuture<>();
        eventPublisher.publishEvent(new HarvestStartedEvent(this, id, result));
        try {
            log.info("Event: " + result);
            log.info("Result is: " + result.get());
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
