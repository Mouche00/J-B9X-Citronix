package org.citronix.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.citronix.utils.constants.Season;
import org.citronix.utils.validation.groups.OnCreate;
import org.citronix.utils.validation.annotations.ValidEnum;

import java.time.Year;

@Builder
public record HarvestRequestDTO(@NotNull(groups = OnCreate.class)
                                @ValidEnum(enumClass = Season.class, message = "Invalid season")
                                String season,

                                @PastOrPresent
                                @NotNull(groups = OnCreate.class)
                                Year year,

                                @NotBlank(groups = OnCreate.class)
                                String fieldId) {
}
