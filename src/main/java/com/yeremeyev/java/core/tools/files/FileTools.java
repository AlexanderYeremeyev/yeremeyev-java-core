package com.yeremeyev.java.core.tools.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileTools {

    public static byte[] readyFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        byte[] results = new byte[(int) file.length()];
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(results);
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return results;
    }

    public static boolean writeFile(String fileName, String fileContent) throws Exception {
        File file = new File(fileName);
        FileWriter fileWriter = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            if (!file.createNewFile()) {
                return false;
            }
            fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
        return true;
    }

    public static boolean createDirectory(String path) {
        File file = new File(path);
        boolean result = true;
        if (!file.exists()) {
            result = file.mkdirs();
        }
        return result;
    }

    public static boolean exist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
