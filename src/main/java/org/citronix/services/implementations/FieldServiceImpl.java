package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.models.Field;
import org.citronix.repositories.FieldRepository;
import org.citronix.services.FieldService;
import org.citronix.utils.mappers.FieldMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository farmRepository;
    private final FieldMapper fieldMapper;

    @Override
    public JpaRepository<Field, UUID> getRepository() {
        return farmRepository;
    }

    @Override
    public GenericMapper<Field, FieldRequestDTO, FieldResponseDTO> getMapper() {
        return fieldMapper;
    }
}
