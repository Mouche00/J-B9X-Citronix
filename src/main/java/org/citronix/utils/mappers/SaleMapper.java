package org.citronix.utils.mappers;

import org.citronix.dtos.request.SaleRequestDTO;
import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.SaleResponseDTO;
import org.citronix.models.Sale;
import org.citronix.models.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper extends GenericMapper<Sale, SaleRequestDTO, SaleResponseDTO> {
    @Mapping(source = "harvestId", target = "harvest.id")
    Sale toEntity(SaleRequestDTO req);
}
