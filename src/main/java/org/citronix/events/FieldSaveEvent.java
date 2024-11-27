package org.citronix.events;

import lombok.Getter;
import lombok.Setter;
import org.citronix.models.Farm;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Setter
@Getter
public class FieldSaveEvent extends GenericEvent<Farm>{
    private final String farmId;

    public FieldSaveEvent(Object source, String farmId) {
        super(source);
        this.farmId = farmId;
    }
}
