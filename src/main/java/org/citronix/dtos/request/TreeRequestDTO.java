package org.citronix.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import org.citronix.utils.enums.Season;
import org.citronix.utils.validation.OnCreate;

import java.time.LocalDate;
import java.time.Year;

@Builder
public record TreeRequestDTO(@PastOrPresent
                             @JsonFormat(pattern="yyyy/MM/dd")
                             LocalDate plantingDate,

                             @NotBlank(groups = OnCreate.class) String fieldId) {
}
