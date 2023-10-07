package com.yeremeyev.java.core.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class FileTools {

    public static byte[] readyFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        byte[] results = new byte[(int) file.length()];
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(results);
            inputStream.close();
        } catch (Exception exception) {
            // TODO:
            return null;
        }
        return results;
    }

    public static boolean writeFile(String fileName, String fileContent) {
        File file = new File(fileName);
        try {
            if (file.exists()) {
                file.delete();
            }
            if (!file.createNewFile()) {
                return false;
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
            fileWriter.close();
        } catch (Exception exception) {
            // TODO:
            return false;
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
        boolean result = file.exists();
        return result;
    }
}
