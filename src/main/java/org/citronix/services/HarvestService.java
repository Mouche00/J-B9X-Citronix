package org.citronix.services;

import org.citronix.dtos.request.HarvestRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.models.Harvest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface HarvestService extends GenericService<Harvest, HarvestRequestDTO, HarvestResponseDTO> {
    List<HarvestDetailResponseDTO> startHarvest(String id) throws ExecutionException, InterruptedException;
}
