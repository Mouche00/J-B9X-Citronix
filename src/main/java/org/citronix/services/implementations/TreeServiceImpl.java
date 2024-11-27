package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.events.FieldSaveEvent;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.events.TreeHarvestStartedEvent;
import org.citronix.events.TreeSaveEvent;
import org.citronix.models.Farm;
import org.citronix.models.Field;
import org.citronix.models.Tree;
import org.citronix.repositories.TreeRepository;
import org.citronix.services.TreeService;
import org.citronix.utils.constants.Season;
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
import java.util.concurrent.ExecutionException;

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
    public TreeResponseDTO save(TreeRequestDTO req) {
        Tree entity = mapper.toEntity(req);
        validateTreeConstraints(entity, entity.getField().getId().toString());

        entity = repository.save(entity);
        return findById(entity.getId().toString());
    }

    @Override
    public TreeResponseDTO update(String id, TreeRequestDTO entity) {
        return findAndExecute(id, (foundEntity) -> {
            validateTreeConstraints(mapper.toEntity(entity), foundEntity.getField().getId().toString());
            return updateExistingEntity(entity, foundEntity);
        });
    }

    public void validateTreeConstraints(Tree tree, String fieldId) {
        TreeSaveEvent event = new TreeSaveEvent(this, fieldId);
        eventPublisher.publishEvent(event);
        try {
            Field field = event.getResult().get();
            List<Tree> trees = field.getTrees();

            if(trees.size() >= field.getSurfaceArea() * 100) {
                throw new IllegalArgumentException("The maximum number of trees has been reached (100 per hectare)");
            }

            int plantingMonth = tree.getPlantingDate().getMonth().getValue();
            if(plantingMonth > Season.SPRING.getEnd() || plantingMonth < Season.SPRING.getStart()) {
                throw new IllegalArgumentException("The tree needs to be planted in the spring season (between March and May)");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
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

    @EventListener
    public void handleTreeHarvestStartedEvent(TreeHarvestStartedEvent treeHarvestStartedEvent) {
        String treeId = treeHarvestStartedEvent.getTreeId();
        Tree tree = findEntityById(treeId);
        if(tree != null) treeHarvestStartedEvent.getResult().complete(tree);
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
