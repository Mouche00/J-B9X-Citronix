package org.citronix.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.citronix.models.Client;

@Builder
public record SaleResponseDTO(String id,
                              double unitPrice,
                              double quantity,
                              HarvestResponseDTO harvest,
                              ClientResponseDTO client) {
}
