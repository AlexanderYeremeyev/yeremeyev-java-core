package com.yeremeyev.java.core.tools.strings;

public class ConverterTools {

    /**
     * @param array     of bytes
     * @param separator symbol to separate result byte values
     * @return string hex value with separator
     */
    public static String byteArrayToHex(byte[] array, String separator) {
        StringBuilder stringBuilder = new StringBuilder(array.length * 2);
        int arrayLength = array.length;
        for (int index = 0; index < arrayLength; index++) {
            if (index != 0) {
                stringBuilder.append(separator);
            }
            byte value = array[index];
            stringBuilder.append(String.format("%02x", value));
        }
        return stringBuilder.toString();
    }
}
