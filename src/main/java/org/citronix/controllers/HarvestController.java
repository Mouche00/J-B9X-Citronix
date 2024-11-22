package org.citronix.controllers;

import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.models.Harvest;
import org.citronix.services.HarvestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/harvests")
public class HarvestController extends GenericController<Harvest, HarvestRequestDTO, HarvestResponseDTO> {
    private final HarvestService service;

    protected HarvestController(HarvestService service, HarvestService harvestService) {
        super(service);
        this.service = harvestService;
    }

}
