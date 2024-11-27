package org.citronix.events;

import lombok.Getter;
import org.citronix.models.Farm;

@Getter
public class SaleSaveEvent extends GenericEvent<Double>{
    private final String harvestId;

    public SaleSaveEvent(Object source, String harvestId) {
        super(source);
        this.harvestId = harvestId;
    }
}
