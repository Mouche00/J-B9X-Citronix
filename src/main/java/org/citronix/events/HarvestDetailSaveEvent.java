package org.citronix.events;

import lombok.Getter;
import org.citronix.models.Farm;

public class HarvestDetailSaveEvent extends GenericEvent<Boolean>{
    private final String treeId;
    private final String harvestId;

    public HarvestDetailSaveEvent(Object source, String treeId, String harvestId) {
        super(source);
        this.treeId = treeId;
        this.harvestId = harvestId;
    }

    public String getTreeId() {
        return treeId;
    }

    public String getHarvestId() {
        return harvestId;
    }
}
