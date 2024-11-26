package org.citronix.utils.mappers;

import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.models.Harvest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HarvestMapper extends GenericMapper<Harvest, HarvestRequestDTO, HarvestResponseDTO>{
    @Mapping(source = "fieldId", target = "field.id")
    Harvest toEntity(HarvestRequestDTO req);
}
