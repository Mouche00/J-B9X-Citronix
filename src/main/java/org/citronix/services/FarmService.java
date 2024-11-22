package org.citronix.services;

import org.citronix.dtos.request.FarmRequestDTO;
import org.citronix.dtos.response.FarmResponseDTO;
import org.citronix.models.Farm;

public interface FarmService extends GenericService<Farm, FarmRequestDTO, FarmResponseDTO> {
}
