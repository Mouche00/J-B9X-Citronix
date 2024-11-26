package org.citronix.services.implementations;

import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.models.Farm;
import org.citronix.repositories.FarmRepository;
import org.citronix.services.FarmService;
import org.citronix.services.GenericService;
import org.citronix.utils.mappers.FarmMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {
    private final FarmRepository repository;
    private final FarmMapper mapper;

    @Override
    public JpaRepository<Farm, UUID> getRepository() {
        return repository;
    }

    @Override
    public GenericMapper<Farm, FarmRequestDTO, FarmResponseDTO> getMapper() {
        return mapper;
    }
}
