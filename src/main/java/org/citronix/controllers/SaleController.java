package org.citronix.controllers;

import org.citronix.dtos.request.SaleRequestDTO;
import org.citronix.dtos.response.SaleResponseDTO;
import org.citronix.models.Sale;
import org.citronix.services.GenericService;
import org.citronix.services.SaleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sales")
public class SaleController extends GenericController<Sale, SaleRequestDTO, SaleResponseDTO> {
    private final SaleService service;
    protected SaleController(SaleService service) {
        super(service);
        this.service = service;
    }
}
