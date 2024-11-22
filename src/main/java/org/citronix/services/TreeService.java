package org.citronix.services;

import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.models.Tree;

import java.util.List;

public interface TreeService extends GenericService<Tree, TreeRequestDTO, TreeResponseDTO> {
    List<TreeResponseDTO> findAllByFieldId(String id);
}
