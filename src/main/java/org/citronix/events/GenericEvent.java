package org.citronix.events;

import lombok.Getter;
import lombok.Setter;
import org.citronix.models.HarvestDetail;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Setter
@Getter
public class GenericEvent<R> extends ApplicationEvent {
    private CompletableFuture<R> result = new CompletableFuture<>();

    public GenericEvent(Object source) {
        super(source);
    }
}
