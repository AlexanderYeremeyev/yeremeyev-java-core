package com.yeremeyev.java.core.tools;

public class StringTools {
    public static final String EMPTY = "";

    public static boolean isNullOrEmpty(String string) {
        if (string == null) {
            return true;
        }
        return string.trim().isEmpty();
    }

    public static boolean hasContent(String string) {
        return !isNullOrEmpty(string);
    }

    public static String emptyIfNull(String string) {
        if (string == null) {
            return EMPTY;
        }
        return string;
    }
}
