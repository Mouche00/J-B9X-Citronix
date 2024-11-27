package org.citronix.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.models.Farm;
import org.citronix.services.FarmService;
import org.citronix.utils.response.ApiResponse;
import org.citronix.utils.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/farms")
public class FarmController extends GenericController<Farm, FarmRequestDTO, FarmResponseDTO> {
    public final FarmService service;

    protected FarmController(FarmService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<FarmResponseDTO>>> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Page<FarmResponseDTO> farms = service.searchFarms(name, location, PageRequest.of(page, size));
        return ResponseEntity.ok(ResponseUtil.success(farms, "Farms fetched successfully", request.getRequestURI()));
    }
}
