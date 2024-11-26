package org.citronix.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;
import org.citronix.services.HarvestDetailService;
import org.citronix.services.HarvestService;
import org.citronix.utils.response.ApiResponse;
import org.citronix.utils.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/harvest-details")
public class HarvestDetailController extends GenericController<HarvestDetail, HarvestDetailRequestDTO, HarvestDetailResponseDTO> {
    private final HarvestDetailService service;

    protected HarvestDetailController(HarvestDetailService service) {
        super(service);
        this.service = service;
    }
}
