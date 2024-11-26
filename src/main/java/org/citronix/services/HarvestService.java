package org.citronix.services;

import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface HarvestService extends GenericService<Harvest, HarvestRequestDTO, HarvestResponseDTO> {
    HarvestResponseDTO startHarvestCalc(String id);
    HarvestResponseDTO saveHarvestCalc(String id, List<HarvestDetail> harvestDetails);
}
