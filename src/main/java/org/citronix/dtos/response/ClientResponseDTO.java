package org.citronix.dtos.response;

import jakarta.validation.constraints.NotBlank;
import org.citronix.utils.validation.groups.OnCreate;

public record ClientResponseDTO(String cin,
                               String name) {
}