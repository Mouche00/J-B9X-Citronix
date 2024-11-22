package org.citronix.services;

import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.models.Harvest;

public interface HarvestService extends GenericService<Harvest, HarvestRequestDTO, HarvestResponseDTO> {
}
