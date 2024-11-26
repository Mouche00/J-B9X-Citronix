package org.citronix.utils.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Season {
    SUMMER(6, 8),
    AUTUMN(9, 11),
    WINTER(12, 2),
    SPRING(3, 5);

    private final int start;
    private final int end;

}
