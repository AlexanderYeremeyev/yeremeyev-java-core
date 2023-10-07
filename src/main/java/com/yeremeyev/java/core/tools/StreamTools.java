package com.yeremeyev.java.core.tools;

import java.io.InputStream;
import java.io.OutputStream;

public class StreamTools {

    public static boolean copy(InputStream inputStream, OutputStream outputStream) {
        final int BUFFER_LENGTH = 1024 * 8;
        byte[] buffer = new byte[BUFFER_LENGTH];
        try {
            int length = -1;
            do {
                length = inputStream.read(buffer);
                if (length != -1) {
                    outputStream.write(buffer, 0, length);
                }
            } while (length != -1);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
