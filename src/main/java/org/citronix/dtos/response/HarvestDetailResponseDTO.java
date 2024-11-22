package org.citronix.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.citronix.utils.validation.OnCreate;

@Builder
public record HarvestDetailResponseDTO(String id,
                                       double quantity,
                                       TreeResponseDTO tree,
                                       HarvestResponseDTO harvest) {
}
