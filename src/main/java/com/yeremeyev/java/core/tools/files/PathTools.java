package com.yeremeyev.java.core.tools.files;

import com.yeremeyev.java.core.tools.strings.StringTools;
import com.yeremeyev.java.core.tools.SystemProperties;

public class PathTools {
    public static final String SLASH_SYMBOL_STRING = "/";
    public static final String BACKSLASH_SYMBOL_STRING = "\\";
    public static final String SEPARATOR_SYMBOL_STRING = "/";

    public static String getTempDirectory() {
        String result = System.getProperty(SystemProperties.JAVA_IO_TMPDIR);
        return result;
    }

    public static String getFileName(String filePath, boolean withExtension) {
        String result = filePath;
        int findPos = filePath.lastIndexOf(BACKSLASH_SYMBOL_STRING);
        if (findPos >= 0) {
            result = filePath.substring(findPos + 1);
        }

        findPos = filePath.lastIndexOf(SLASH_SYMBOL_STRING);
        if (findPos >= 0) {
            result = filePath.substring(findPos + 1);
        }

        if (!withExtension) {
            findPos = result.lastIndexOf(".");
            if (findPos >= 0) {
                result = result.substring(0, findPos);
            }
        }
        return result;
    }

    public static String getFileDirectory(String filePath) {
        String result = filePath;
        int findPos = filePath.lastIndexOf(BACKSLASH_SYMBOL_STRING);
        if (findPos >= 0) {
            result = filePath.substring(0, findPos);
        }

        findPos = filePath.lastIndexOf(SLASH_SYMBOL_STRING);
        if (findPos >= 0) {
            result = filePath.substring(0, findPos);
        }
        return result;
    }

    /*
     * quite often file paths are different because of separator symbols.
     * This function change them in one way.
     */
    public static String normalizeFilePath(String filePath) {
        if (StringTools.isNullOrEmpty(filePath)) {
            return filePath;
        }
        String doubleBackslashForRegexp = BACKSLASH_SYMBOL_STRING + BACKSLASH_SYMBOL_STRING;
        filePath = filePath.replaceAll(doubleBackslashForRegexp, SLASH_SYMBOL_STRING);
        String doubleSlash = SLASH_SYMBOL_STRING + SLASH_SYMBOL_STRING;
        while (filePath.contains(doubleSlash)) {
            filePath = filePath.replaceAll(doubleSlash, SLASH_SYMBOL_STRING);
        }
        return filePath;
    }
}
