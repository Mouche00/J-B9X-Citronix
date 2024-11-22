package org.citronix.controllers;

import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.models.Field;
import org.citronix.services.FieldService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fields")
public class FieldController extends GenericController<Field, FieldRequestDTO, FieldResponseDTO> {

    protected FieldController(FieldService service) {
        super(service);
    }
}
