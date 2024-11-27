package org.citronix.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.citronix.models.Client;

public record SaleResponseDTO(String id,
                              double unitPrice,
                              double quantity,
                              HarvestResponseDTO harvest,
                              ClientResponseDTO client) {
}
