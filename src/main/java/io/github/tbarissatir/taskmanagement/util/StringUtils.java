package io.github.tbarissatir.taskmanagement.util;

public class StringUtils {

    public static String normalize(String input) {
        if (input == null) return null;
        return input.trim().replaceAll("\\s+", " ");
    }
}
