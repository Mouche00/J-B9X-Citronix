package org.citronix.controllers;

import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.models.Farm;
import org.citronix.services.FarmService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/farms")
public class FarmController extends GenericController<Farm, FarmRequestDTO, FarmResponseDTO> {

    protected FarmController(FarmService service) {
        super(service);
    }
}
