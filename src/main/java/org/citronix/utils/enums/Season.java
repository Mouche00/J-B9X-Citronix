package org.citronix.utils.enums;

public enum Season {
    SUMMER("SUMMER", 6, 8),
    AUTUMN("AUTUMN", 9, 11),
    WINTER("WINTER", 12, 2),
    SPRING("WINTER", 3, 5);

    private final String name;
    private final int start;
    private final int end;

    Season(String name, int start, int end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
