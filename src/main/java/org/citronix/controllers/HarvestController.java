package org.citronix.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;
import org.citronix.services.HarvestService;
import org.citronix.utils.StringUtil;
import org.citronix.utils.response.ApiResponse;
import org.citronix.utils.response.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/harvests")
public class HarvestController extends GenericController<Harvest, HarvestRequestDTO, HarvestResponseDTO> {
    private final HarvestService service;

    protected HarvestController(HarvestService service, HarvestService harvestService) {
        super(service);
        this.service = harvestService;
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ApiResponse<HarvestResponseDTO>> startHarvest(@PathVariable String id, HttpServletRequest request) {
        HarvestResponseDTO result = service.startHarvestCalc(id);
        return ResponseEntity.ok(ResponseUtil.success(result, "Harvest started successfully", request.getRequestURI()));
    }
}
