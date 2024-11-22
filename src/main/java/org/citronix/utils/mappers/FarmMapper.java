package org.citronix.utils.mappers;

import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.models.Farm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FarmMapper extends GenericMapper<Farm, FarmRequestDTO, FarmResponseDTO>{
}
