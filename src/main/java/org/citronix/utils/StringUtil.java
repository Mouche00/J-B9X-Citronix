package org.citronix.utils;

public class StringUtil {

    public static String extractBaseName(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        return input.replaceAll("ResponseDTO", "");
    }
}
