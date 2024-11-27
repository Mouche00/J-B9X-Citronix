package org.citronix.dtos.request;

import jakarta.validation.constraints.NotBlank;
import org.citronix.utils.validation.groups.OnCreate;

public record ClientRequestDTO(@NotBlank(groups = OnCreate.class) String cin,
                               String name) {
}
