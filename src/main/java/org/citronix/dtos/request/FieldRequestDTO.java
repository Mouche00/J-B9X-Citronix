package org.citronix.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.citronix.models.Farm;
import org.citronix.utils.validation.annotations.Exists;
import org.citronix.utils.validation.groups.OnCreate;

@Builder
public record FieldRequestDTO(@NotNull(groups = OnCreate.class) @DecimalMin(value = "0.1") Double surfaceArea,
                              @NotBlank(groups = OnCreate.class) @Exists(entity = Farm.class, groups = OnCreate.class) String farmId) {
}
