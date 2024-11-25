package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.models.HarvestDetail;
import org.citronix.repositories.HarvestDetailRepository;
import org.citronix.services.HarvestDetailService;
import org.citronix.utils.constants.TreeProductivity;
import org.citronix.utils.mappers.GenericMapper;
import org.citronix.utils.mappers.HarvestDetailMapper;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class HarvestDetailServiceImpl implements HarvestDetailService {
    private final HarvestDetailRepository repository;
    private final HarvestDetailMapper mapper;

    @Override
    public JpaRepository<HarvestDetail, UUID> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<HarvestDetail, HarvestDetailRequestDTO, HarvestDetailResponseDTO> getMapper() {
        return mapper;
    }

    @EventListener(condition = "#harvestStartedEvent.trees != null or !#harvestStartedEvent.trees.isEmpty()")
    public void handleHarvestStartedEvent(HarvestStartedEvent harvestStartedEvent) {
        List<TreeResponseDTO> trees = harvestStartedEvent.getTrees();
        List<HarvestDetailRequestDTO> details = trees.stream()
                .map(t ->
                        HarvestDetailRequestDTO.builder()
                                .harvestId(harvestStartedEvent.getHarvestId())
                                .treeId(t.id())
                                .quantity(this.calculateQuantity(t.age()))
                                .build())
                .toList();
        List<HarvestDetailResponseDTO> result = this.saveAll(details);
        if(!result.isEmpty()) harvestStartedEvent.getResult().complete(result);
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
