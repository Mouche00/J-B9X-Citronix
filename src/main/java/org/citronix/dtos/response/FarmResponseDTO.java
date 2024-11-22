package org.citronix.dtos.response;


import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FarmResponseDTO(String id,
                              String name,
                              String location,
                              double surfaceArea,
                              LocalDate createdAt) {
}
