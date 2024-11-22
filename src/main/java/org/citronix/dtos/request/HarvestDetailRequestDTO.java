package org.citronix.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.citronix.utils.enums.Season;
import org.citronix.utils.validation.OnCreate;

import java.time.Year;

@Builder
public record HarvestDetailRequestDTO(@NotNull(groups = OnCreate.class)
                                      @Positive
                                      double quantity,

                                      @NotBlank(groups = OnCreate.class)
                                      String treeId,

                                      @NotBlank(groups = OnCreate.class)
                                      String harvestId) {
}
