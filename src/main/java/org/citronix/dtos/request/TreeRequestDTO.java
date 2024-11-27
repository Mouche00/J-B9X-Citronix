package org.citronix.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import org.citronix.models.Field;
import org.citronix.utils.validation.annotations.Exists;
import org.citronix.utils.validation.groups.OnCreate;

import java.time.LocalDate;

@Builder
public record TreeRequestDTO(@PastOrPresent
                             @JsonFormat(pattern="yyyy/MM/dd")
                             LocalDate plantingDate,

                             @NotBlank(groups = OnCreate.class)
                             @Exists(entity = Field.class, groups = OnCreate.class)
                             String fieldId) {
}
