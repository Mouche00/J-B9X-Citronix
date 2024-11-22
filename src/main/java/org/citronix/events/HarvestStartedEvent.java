package org.citronix.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

@Getter
public class HarvestStartedEvent extends ApplicationEvent {
    private final String harvestId;
    public HarvestStartedEvent(Object source, String harvestId) {
        super(source);
        this.harvestId = harvestId;
    }
}
