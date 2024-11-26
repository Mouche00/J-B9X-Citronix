package org.citronix.dtos.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TreeResponseDTO(String id,
                              LocalDate plantingDate,
                              int age,
                              FieldResponseDTO field) {
}
