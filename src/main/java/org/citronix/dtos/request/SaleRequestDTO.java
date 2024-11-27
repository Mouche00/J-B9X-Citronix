package org.citronix.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.citronix.models.Client;

@Builder
public record SaleRequestDTO(@Positive
                             double unitPrice,
                             @Positive
                             double quantity,
                             @NotBlank
                             String harvestId,
                             ClientRequestDTO client) {
}
