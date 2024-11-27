package org.citronix.events;

import lombok.Getter;
import org.citronix.dtos.response.RevenueDTO;

@Getter
public class RevenueCalcStartedEvent extends GenericEvent<RevenueDTO>{
    private final String farmId;

    public RevenueCalcStartedEvent(Object source, String farmId) {
        super(source);
        this.farmId = farmId;
    }
}
