package org.citronix.utils.mappers;

import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.models.HarvestDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper extends GenericMapper<HarvestDetail, HarvestDetailRequestDTO, HarvestDetailResponseDTO>{
    @Mappings({
            @Mapping(source = "treeId", target = "tree.id"),
            @Mapping(source = "harvestId", target = "harvest.id")
    })
    HarvestDetail toEntity(HarvestDetailRequestDTO req);

    HarvestDetailResponseDTO toDTO(HarvestDetail entity);
}
