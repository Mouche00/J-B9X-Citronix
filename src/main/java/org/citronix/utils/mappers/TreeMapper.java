package org.citronix.utils.mappers;

import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.models.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper extends GenericMapper<Tree, TreeRequestDTO, TreeResponseDTO>{
    @Mapping(source = "fieldId", target = "field.id")
    Tree toEntity(TreeRequestDTO req);
    @Mapping(source = "field", target = "field")
    TreeResponseDTO toDTO(Tree entity);
}
