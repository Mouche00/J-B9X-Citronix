package org.citronix.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import org.citronix.utils.enums.Season;
import org.citronix.utils.validation.OnCreate;

import java.time.Year;

@Builder
public record HarvestRequestDTO(@NotNull Season season,
                                @PastOrPresent @NotNull(groups = OnCreate.class) Year year,
                                @NotBlank(groups = OnCreate.class) String fieldId) {
}
