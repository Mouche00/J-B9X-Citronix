package org.citronix.events;

import lombok.Getter;
import org.citronix.models.Tree;
import org.springframework.context.ApplicationEvent;

import java.util.concurrent.CompletableFuture;

@Getter
public class TreeHarvestStartedEvent extends GenericEvent<Tree> {
    private final String treeId;

    public TreeHarvestStartedEvent(Object source, String treeId) {
        super(source);
        this.treeId = treeId;
    }
}
