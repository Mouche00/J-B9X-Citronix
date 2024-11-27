package org.citronix.events;

import lombok.Getter;
import org.citronix.models.Farm;
import org.citronix.models.Field;

@Getter
public class TreeSaveEvent extends GenericEvent<Field>{
    private final String fieldId;

    public TreeSaveEvent(Object source, String fieldId) {
        super(source);
        this.fieldId = fieldId;
    }
}
