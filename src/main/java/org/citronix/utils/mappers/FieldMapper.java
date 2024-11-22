package org.citronix.utils.mappers;

import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.models.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FieldMapper extends GenericMapper<Field, FieldRequestDTO, FieldResponseDTO>{
    @Mapping(source = "farmId", target = "farm.id")
    Field toEntity(FieldRequestDTO req);
}
