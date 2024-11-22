package org.citronix.services;

import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.models.Tree;

public interface TreeService extends GenericService<Tree, TreeRequestDTO, TreeResponseDTO> {
}
