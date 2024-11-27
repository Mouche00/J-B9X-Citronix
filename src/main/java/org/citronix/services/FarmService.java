package org.citronix.services;

import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.dtos.response.RevenueDTO;
import org.citronix.models.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FarmService extends GenericService<Farm, FarmRequestDTO, FarmResponseDTO> {
    Page<FarmResponseDTO> searchFarms(String name, String location, Pageable pageable);
    RevenueDTO calculateRevenue(String farmId);
}
