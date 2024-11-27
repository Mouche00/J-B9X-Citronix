package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.events.FieldSaveEvent;
import org.citronix.models.Farm;
import org.citronix.repositories.FarmRepository;
import org.citronix.services.FarmService;
import org.citronix.services.GenericService;
import org.citronix.utils.mappers.FarmMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
public class FarmServiceImpl extends GenericServiceImpl<Farm, FarmRequestDTO, FarmResponseDTO> implements FarmService {
    private final FarmRepository repository;
    private final FarmMapper mapper;

    public FarmServiceImpl(FarmRepository repository, FarmMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @EventListener
    public void handleFieldSaveEvent(FieldSaveEvent fieldSaveEvent) {
        Farm farm = findEntityById(fieldSaveEvent.getFarmId());
        if(farm != null) fieldSaveEvent.getResult().complete(farm);
    }
}
