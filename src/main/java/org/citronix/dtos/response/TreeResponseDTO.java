package org.citronix.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import org.citronix.utils.validation.OnCreate;

import java.time.LocalDate;

@Builder
public record TreeResponseDTO(String id,
                              LocalDate plantingDate,
                              FieldResponseDTO field) {
}
