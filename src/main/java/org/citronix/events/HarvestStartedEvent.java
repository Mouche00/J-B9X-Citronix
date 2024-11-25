package org.citronix.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Setter
@Getter
public class HarvestStartedEvent extends ApplicationEvent {
    private final String harvestId;
    private List<TreeResponseDTO> trees = new ArrayList<>();
    private final CompletableFuture<List<HarvestDetailResponseDTO>> result;

    public HarvestStartedEvent(Object source, String harvestId, CompletableFuture<List<HarvestDetailResponseDTO>> result) {
        super(source);
        this.harvestId = harvestId;
        this.result = result;
    }
}
