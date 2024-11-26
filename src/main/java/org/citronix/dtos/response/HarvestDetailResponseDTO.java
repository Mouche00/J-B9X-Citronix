package org.citronix.dtos.response;

import lombok.Builder;

@Builder
public record HarvestDetailResponseDTO(String id,
                                       double quantity,
                                       TreeResponseDTO tree) {
}
