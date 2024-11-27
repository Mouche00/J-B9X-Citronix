package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.events.HarvestDetailSaveEvent;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.events.TreeHarvestStartedEvent;
import org.citronix.models.*;
import org.citronix.repositories.HarvestDetailRepository;
import org.citronix.repositories.HarvestDetailRepository;
import org.citronix.repositories.TreeRepository;
import org.citronix.services.HarvestDetailService;
import org.citronix.utils.constants.TreeProductivity;
import org.citronix.utils.mappers.HarvestDetailMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.citronix.utils.mappers.HarvestDetailMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.citronix.repositories.TreeRepository.Specs.byFieldHarvestId;


@Slf4j
@Transactional
@Service
public class HarvestDetailServiceImpl extends GenericServiceImpl<HarvestDetail, HarvestDetailRequestDTO, HarvestDetailResponseDTO> implements HarvestDetailService {
    private final HarvestDetailRepository repository;
    private final TreeRepository treeRepository;
    private final HarvestDetailMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public HarvestDetailServiceImpl(HarvestDetailRepository repository, TreeRepository treeRepository, HarvestDetailMapper mapper, ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.repository = repository;
        this.treeRepository = treeRepository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public HarvestDetailResponseDTO save(HarvestDetailRequestDTO entity) {
        HarvestDetail harvestDetail = appendQuantity(mapper.toEntity(entity));
        validateHarvestDetailConstraints(entity.treeId(), entity.harvestId());
        return mapper.toDTO(
                repository.save(
                        harvestDetail));
    }

    public void validateHarvestDetailConstraints(String treeId, String harvestId) {
        HarvestDetailSaveEvent event = new HarvestDetailSaveEvent(this, treeId, harvestId);
        eventPublisher.publishEvent(event);

        try {
            boolean exists = event.getResult().get();
            if(!exists) {
                throw new IllegalArgumentException("No harvest exists for this tree");
            }

            if(existsByHarvestAndTree(harvestId, treeId)) {
                throw new IllegalArgumentException("A harvest already exists for this tree");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void handleHarvestStartedEvent(HarvestStartedEvent harvestStartedEvent) {
        String harvestId = harvestStartedEvent.getHarvestId();
        List<Tree> trees = treeRepository.findAll(byFieldHarvestId(UUID.fromString(harvestId)));

        List<Tree> notYetHarvested = trees.stream()
                .filter(t -> !existsByHarvestAndTree(harvestId, String.valueOf(t.getId())))
                .toList();

        if(notYetHarvested.isEmpty()) {
            throw new IllegalArgumentException("All trees have already been harvested.");
        }

        List<HarvestDetail> details = notYetHarvested.stream()
                .map(t ->
                        HarvestDetail.builder()
                                .harvest(Harvest.builder().id(UUID.fromString(harvestStartedEvent.getHarvestId())).build())
                                .tree(Tree.builder().id(UUID.fromString(String.valueOf(t.getId()))).build())
                                .quantity(this.calculateQuantity(t.getAge()))
                                .build())
                .toList();
        if(!details.isEmpty()) harvestStartedEvent.getResult().complete(details);
    }

    public boolean existsByHarvestAndTree(String harvestId, String treeId) {
        return repository.existsByHarvestAndTree(UUID.fromString(harvestId), UUID.fromString(treeId));
    }

    public int getTreeAge(String treeId) {
//        CompletableFuture<Tree> result = new CompletableFuture<>();
        TreeHarvestStartedEvent event = new TreeHarvestStartedEvent(this, treeId);
        eventPublisher.publishEvent(event);
        try {
            Tree tree = event.getResult().get();
            return tree.getAge();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error getting tree age " + e);
        }

    }

    public HarvestDetail appendQuantity(HarvestDetail harvestDetail) {
        String treeId = harvestDetail.getTree().getId().toString();
        int treeAge = getTreeAge(treeId);
        double quantity = calculateQuantity(treeAge);
        harvestDetail.setQuantity(quantity);
        return harvestDetail;
    }

    public double calculateQuantity(int age) {
        for (TreeProductivity treeProductivity : TreeProductivity.values()) {
            if (age <= treeProductivity.getMaxAge()) {
                return treeProductivity.getProductivity();
            }
        }
        return 0;
    }
}
