package org.citronix.dtos.response;

import lombok.Builder;
import org.citronix.utils.constants.Season;

import java.time.Year;
import java.util.List;

@Builder
public record HarvestResponseDTO(String id,
                                 Season season,
                                 Year year,
                                 double TotalQuantity,
                                 FieldResponseDTO field,
                                 List<HarvestDetailResponseDTO> harvestDetails) {
}
