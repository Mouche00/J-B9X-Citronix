package org.citronix.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.citronix.dtos.request.HarvestDetailRequestDTO;
import org.citronix.dtos.response.HarvestDetailResponseDTO;
import org.citronix.dtos.response.HarvestResponseDTO;
import org.citronix.dtos.response.TreeResponseDTO;
import org.citronix.models.HarvestDetail;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Setter
@Getter
public class HarvestStartedEvent extends GenericEvent<List<HarvestDetail>> {
    private final String harvestId;
    private List<TreeResponseDTO> trees;

    public HarvestStartedEvent(Object source, String harvestId) {
        super(source);
        this.harvestId = harvestId;
    }
}
