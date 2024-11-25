package org.citronix.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.citronix.utils.validation.groups.OnCreate;

@Builder
public record HarvestDetailRequestDTO(@NotNull(groups = OnCreate.class)
                                      @PositiveOrZero
                                      Double quantity,

                                      @NotBlank(groups = OnCreate.class)
                                      String treeId,

                                      @NotBlank(groups = OnCreate.class)
                                      String harvestId) {
}
