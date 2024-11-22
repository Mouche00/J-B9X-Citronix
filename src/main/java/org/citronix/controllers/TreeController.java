package org.citronix.controllers;

import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.models.Tree;
import org.citronix.services.TreeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/trees")
public class TreeController extends GenericController<Tree, TreeRequestDTO, TreeResponseDTO> {

    protected TreeController(TreeService service) {
        super(service);
    }
}
