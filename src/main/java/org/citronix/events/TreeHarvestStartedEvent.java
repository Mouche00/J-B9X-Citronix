package org.citronix.events;

import lombok.Getter;
import org.citronix.models.Tree;
import org.springframework.context.ApplicationEvent;

import java.util.concurrent.CompletableFuture;

@Getter
public class TreeHarvestStartedEvent extends ApplicationEvent {
    private final String treeId;
    private final CompletableFuture<Tree> result;

    public TreeHarvestStartedEvent(Object source, String treeId, CompletableFuture<Tree> result) {
        super(source);
        this.treeId = treeId;
        this.result = result;
    }
}
