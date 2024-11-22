package org.citronix.controllers;

import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.models.Farm;
import org.citronix.services.FarmService;
import org.citronix.services.GenericService;
import org.citronix.utils.validation.OnCreate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/farms")
public class FarmController extends GenericController<Farm, FarmRequestDTO, FarmResponseDTO> {

    protected FarmController(FarmService service) {
        super(service);
    }
}
