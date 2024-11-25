package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.models.Tree;
import org.citronix.repositories.TreeRepository;
import org.citronix.services.TreeService;
import org.citronix.utils.mappers.GenericMapper;
import org.citronix.utils.mappers.TreeMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

import static org.citronix.repositories.TreeRepository.Specs.byFieldHarvestId;

@Slf4j
@Transactional
@Service
public class TreeServiceImpl extends GenericServiceImpl<Tree, TreeRequestDTO, TreeResponseDTO> implements TreeService {
    private final TreeRepository repository;
    private final TreeMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public TreeServiceImpl(TreeRepository repository, TreeMapper mapper, ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public List<TreeResponseDTO> findAllByFieldId(String fieldId) {
        return mapper.toDTOs(executeWithUUID(fieldId, repository::findByFieldId));
    }

    @Override
    public List<Tree> findAllEntitiesByFieldHarvestId(String harvestId) {
        return executeWithUUID(harvestId, (id) -> repository.findAll(byFieldHarvestId(id)));
    }

    @EventListener(condition = "#harvestStartedEvent.trees == null or #harvestStartedEvent.trees.isEmpty()")
    public void handleHarvestStartedEvent(HarvestStartedEvent harvestStartedEvent) {
        String harvestId = harvestStartedEvent.getHarvestId();
        List<Tree> trees = appendAgeAll(findAllEntitiesByFieldHarvestId(harvestId));
        harvestStartedEvent.setTrees(mapper.toDTOs(trees));
        eventPublisher.publishEvent(harvestStartedEvent);
    }

    public List<Tree> appendAgeAll(List<Tree> trees) {
        return trees.stream().map(this::appendAge).toList();
    }

    public Tree appendAge(Tree tree) {
        int age = calculateAge(tree.getPlantingDate());
        return tree.withAge(age);
    }

    private int calculateAge(LocalDate plantingDate) {
        return plantingDate != null && !plantingDate.isAfter(LocalDate.now())
                ? Period.between(plantingDate, LocalDate.now()).getYears()
                : 0;
    }
}
