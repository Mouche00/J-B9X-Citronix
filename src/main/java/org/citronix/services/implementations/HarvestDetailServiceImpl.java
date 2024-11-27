package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.events.TreeHarvestStartedEvent;
import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;
import org.citronix.models.Tree;
import org.citronix.repositories.HarvestDetailRepository;
import org.citronix.repositories.HarvestDetailRepository;
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

@Slf4j
@Transactional
@Service
public class HarvestDetailServiceImpl extends GenericServiceImpl<HarvestDetail, HarvestDetailRequestDTO, HarvestDetailResponseDTO> implements HarvestDetailService {
    private final HarvestDetailRepository repository;
    private final HarvestDetailMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public HarvestDetailServiceImpl(HarvestDetailRepository repository, HarvestDetailMapper mapper, ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public HarvestDetailResponseDTO save(HarvestDetailRequestDTO entity) {
        HarvestDetail harvestDetail = appendQuantity(mapper.toEntity(entity));
        return mapper.toDTO(
                repository.save(
                        harvestDetail));
    }

    @EventListener(condition = "#harvestStartedEvent.trees != null or !#harvestStartedEvent.trees.isEmpty()")
    public void handleHarvestStartedEvent(HarvestStartedEvent harvestStartedEvent) {
        List<TreeResponseDTO> trees = harvestStartedEvent.getTrees();
//        List<HarvestDetailRequestDTO> details = trees.stream()
//                .map(t ->
//                        HarvestDetailRequestDTO.builder()
//                                .harvestId(harvestStartedEvent.getHarvestId())
//                                .treeId(t.id())
//                                .quantity(this.calculateQuantity(t.age()))
//                                .build())
//                .toList();
        List<HarvestDetail> details = trees.stream()
                .map(t ->
                        HarvestDetail.builder()
                                .harvest(Harvest.builder().id(UUID.fromString(harvestStartedEvent.getHarvestId())).build())
                                .tree(Tree.builder().id(UUID.fromString(t.id())).build())
                                .quantity(this.calculateQuantity(t.age()))
                                .build())
                .toList();
//        List<HarvestDetailResponseDTO> result = this.saveAll(details);
//        if(!details.isEmpty()) harvestStartedEvent.getResult().complete(result);
//        List<HarvestDetail> details1 = mapper.toEntities(details);
        if(!details.isEmpty()) harvestStartedEvent.getResult().complete(details);
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
