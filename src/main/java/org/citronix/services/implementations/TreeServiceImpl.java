package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.events.HarvestStartedEvent;
import org.citronix.models.Tree;
import org.citronix.repositories.TreeRepository;
import org.citronix.services.TreeService;
import org.citronix.utils.mappers.GenericMapper;
import org.citronix.utils.mappers.TreeMapper;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements TreeService {
    private final TreeRepository repository;
    private final TreeMapper mapper;

    @Override
    public JpaRepository<Tree, UUID> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<Tree, TreeRequestDTO, TreeResponseDTO> getMapper() {
        return mapper;
    }

    @Override
    public List<TreeResponseDTO> findAllByFieldId(String id) {
        return mapper.toDTOs(repository.findByField_Id(UUID.fromString(id)));
    }

    @EventListener
    public void handleHarvestStartedEvent(HarvestStartedEvent harvestStartedEvent) {

    }
}
