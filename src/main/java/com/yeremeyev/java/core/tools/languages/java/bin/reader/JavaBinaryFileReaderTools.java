package com.yeremeyev.java.core.tools.languages.java.bin.reader;

import com.yeremeyev.java.core.tools.files.FileTools;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * class contains methods to read "jar" and "class" files
 */
public class JavaBinaryFileReaderTools {
    private static final String JAR_FILE_POSTFIX = ".jar";
    private static final String CLASS_FILE_POSTFIX = ".class";

    /**
     * @param filePath path to jar file
     * @return map "className, classContent"
     */
    private static Map<String, byte[]> loadJarClasses(String filePath) throws Exception {
        Map<String, byte[]> classesMap = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            do {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if (zipEntry == null) {
                    break;
                }
                if (zipEntry.isDirectory()) {
                    zipInputStream.closeEntry();
                    continue;
                }
                String classPath = zipEntry.getName().toLowerCase();
                if (!classPath.endsWith(".class")) {
                    zipInputStream.closeEntry();
                    continue;
                }

                int bytesRead;
                byte[] tempBuffer = new byte[8192 * 2];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                // for some libraries getSize return -1. SoapUI-OS xalan-2.7.1.jar
                // at the same time it read input stream correctly.
                //long size = zipEntry.getSize();
                //if (size > 0) {
                while ((bytesRead = zipInputStream.read(tempBuffer)) != -1) {
                    byteArrayOutputStream.write(tempBuffer, 0, bytesRead);
                }
                //}
                byte classByteData[] = byteArrayOutputStream.toByteArray();
                classesMap.put(classPath, classByteData);

                zipInputStream.closeEntry();
            } while (true);

        } catch (Exception exception) {
            throw exception;
        }
        return classesMap;
    }

    /**
     * @param filePath path to "*.class" file
     * @return map "className, classContent"
     */
    private static Map<String, byte[]> loadClassContent(String filePath) throws Exception {
        Map<String, byte[]> classesMap = new HashMap<>();
        byte classBytes[] = FileTools.readyFile(filePath);
        classesMap.put(filePath, classBytes);
        return classesMap;
    }

    /*
     * classPath, classData
     */
    public static Map<String, byte[]> readFileClasses(String filePath) throws Exception {
        if (filePath.endsWith(JAR_FILE_POSTFIX)) {
            Map<String, byte[]> classesMap = loadJarClasses(filePath);
            return classesMap;
        }
        if (filePath.endsWith(CLASS_FILE_POSTFIX)) {
            Map<String, byte[]> classesMap = loadClassContent(filePath);
            return classesMap;
        }
        Map<String, byte[]> emptyResults = new HashMap<>();
        return emptyResults;
    }
}
