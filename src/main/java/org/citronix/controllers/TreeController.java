package org.citronix.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.request.TreeRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.models.Tree;
import org.citronix.services.TreeService;
import org.citronix.utils.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/trees")
public class TreeController extends GenericController<Tree, TreeRequestDTO, TreeResponseDTO> {

    protected TreeController(TreeService service) {
        super(service);
    }
}
