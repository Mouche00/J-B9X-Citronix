package org.citronix.events;

import lombok.Getter;
import org.citronix.models.Farm;

import java.util.concurrent.CompletableFuture;

@Getter
public class FieldSaveEvent extends GenericEvent<Farm>{
    private final String farmId;

    public FieldSaveEvent(Object source, String farmId) {
        super(source);
        this.farmId = farmId;
    }
}
