package org.citronix.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.citronix.utils.validation.OnCreate;

@Builder
public record FieldRequestDTO(@DecimalMin(value = "0.1") double surfaceArea,
                              @NotBlank(groups = OnCreate.class) String farmId) {
}
