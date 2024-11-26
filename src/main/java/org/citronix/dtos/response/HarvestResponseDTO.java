package org.citronix.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import org.citronix.utils.enums.Season;
import org.citronix.utils.validation.OnCreate;

import java.time.Year;

@Builder
public record HarvestResponseDTO(String id,
                                 Season season,
                                 Year year,
                                 String fieldId) {
}
