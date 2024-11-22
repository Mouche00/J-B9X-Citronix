package org.citronix.dtos.response;


import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;
import org.citronix.dtos.request.FarmRequestDTO;

import java.time.LocalDate;

@Builder
public record FieldResponseDTO(String id,
                               double surfaceArea,
                               FarmResponseDTO farm) {
}
