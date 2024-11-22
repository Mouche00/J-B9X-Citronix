package org.citronix.events;

import lombok.Getter;
import org.citronix.dtos.response.TreeResponseDTO;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class HarvestTreesFetchedEvent extends ApplicationEvent {
    private final String harvestId;
    private final List<TreeResponseDTO> trees;

    public HarvestTreesFetchedEvent(Object source, String harvestId, List<TreeResponseDTO> trees) {
        super(source);
        this.harvestId = harvestId;
        this.trees = trees;
    }
}
