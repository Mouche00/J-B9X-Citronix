package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.models.Field;
import org.citronix.repositories.FieldRepository;
import org.citronix.repositories.FieldRepository;
import org.citronix.services.FieldService;
import org.citronix.utils.mappers.FieldMapper;
import org.citronix.utils.mappers.FieldMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
public class FieldServiceImpl extends GenericServiceImpl<Field, FieldRequestDTO, FieldResponseDTO> implements FieldService {
    private final FieldRepository repository;
    private final FieldMapper mapper;

    public FieldServiceImpl(FieldRepository repository, FieldMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }
}
