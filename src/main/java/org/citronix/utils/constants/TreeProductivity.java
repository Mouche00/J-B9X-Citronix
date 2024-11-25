package org.citronix.utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TreeProductivity {
    YOUNG(2, 2.5),
    MATURE(10, 12),
    OLD(20, 20);

    private final int maxAge;
    private final double productivity;

}
