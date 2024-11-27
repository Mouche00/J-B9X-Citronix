package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.events.HarvestDetailSaveEvent;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;
import org.citronix.repositories.HarvestRepository;
import org.citronix.services.HarvestService;
import org.citronix.utils.constants.Season;
import org.citronix.utils.mappers.HarvestMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Transactional
@Service
public class HarvestServiceImpl extends GenericServiceImpl<Harvest, HarvestRequestDTO, HarvestResponseDTO> implements HarvestService {
    private final HarvestRepository repository;
    private final HarvestMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public HarvestServiceImpl(HarvestRepository repository, HarvestMapper mapper, ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public HarvestResponseDTO save(HarvestRequestDTO req) {
        Harvest entity = mapper.toEntity(req);
        validateHarvestConstraints(entity, entity.getField().getId().toString());

        entity = repository.save(entity);
        return findById(entity.getId().toString());
    }

    @Override
    public HarvestResponseDTO update(String id, HarvestRequestDTO entity) {
        return findAndExecute(id, (foundEntity) -> {
            validateHarvestConstraints(mapper.toEntity(entity), foundEntity.getField().getId().toString());
            return updateExistingEntity(entity, foundEntity);
        });
    }
    public void validateHarvestConstraints(Harvest harvest, String fieldId) {
        if(repository.existsByFieldAndSeason(UUID.fromString(fieldId), harvest.getSeason())) {
            throw new IllegalArgumentException("A harvest already exists for this field in this season");
        }
    }

    @Override
    public HarvestResponseDTO startHarvestCalc(String id) {
        findById(id);
        CompletableFuture<List<HarvestDetail>> result = new CompletableFuture<>();
        HarvestStartedEvent event = new HarvestStartedEvent(this, id);
        eventPublisher.publishEvent(event);
        try {
//            log.info("Event: " + result);
//            log.info("Result is: " + result.get());
            return saveHarvestCalc(id, event.getResult().get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void handleHarvestDetailSaveEvent(HarvestDetailSaveEvent harvestDetailSaveEvent) {
        Boolean harvestExists = repository.existsByTree(UUID.fromString(harvestDetailSaveEvent.getHarvestId()), UUID.fromString(harvestDetailSaveEvent.getTreeId()));
        if(harvestExists != null) harvestDetailSaveEvent.getResult().complete(harvestExists);
    }

    @Override
    public HarvestResponseDTO saveHarvestCalc(String id, List<HarvestDetail> harvestDetails) {
        return findAndExecute(id, (h) -> {
            h.setHarvestDetails(new ArrayList<>(harvestDetails));
            return mapper.toDTO(
                    repository.save(h));
        });
    }
}
