package com.yeremeyev.java.core.tools.resources;

import com.yeremeyev.java.core.logger.Logger;
import com.yeremeyev.java.core.tools.SystemProperties;
import com.yeremeyev.java.core.tools.files.PathTools;

import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ResourceTools {
    private static ResourceTools instance;
    private static Logger logger = Logger.getInstance(ResourceTools.class);
    private Map<String, List> fileResourcesMap = new HashMap<>();

    private ResourceTools() {

    }

    public static ResourceTools getInstance() {
        if (instance == null) {
            instance = new ResourceTools();
        }
        return instance;
    }

    public static Collection<String> getClassPathElements() {
        String pathSeparator = System.getProperty(SystemProperties.PATH_SEPARATOR, ";");
        String classPath = System.getProperty(SystemProperties.JAVA_CLASS_PATH, "");
        String resultsArray[] = classPath.split(pathSeparator);

        List<String> results = new ArrayList<>(Arrays.asList(resultsArray));
        return results;
    }

    private static Collection<String> getResourcesFromJarFile(final File file) {
        final ArrayList<String> retval = new ArrayList<String>();
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(file);
        } catch (final ZipException zipException) {
            logger.logError("Cannot open zip file \"" + file + "\". " + zipException.getMessage());
            return retval;
        } catch (final IOException ioException) {
            logger.logError("Cannot read zip file \"" + file + "\". " + ioException.getMessage());
            return retval;
        }
        final Enumeration entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            if (!zipEntry.isDirectory()) {
                final String fileName = zipEntry.getName();
                retval.add("jar:file:/" + file.getAbsolutePath() + "!/" + fileName);
            }
        }
        try {
            zipFile.close();
        } catch (final IOException e1) {
            throw new Error(e1);
        }
        return retval;
    }

    private static Collection<String> getResourcesFromDirectory(final File directory) {
        final ArrayList<String> retval = new ArrayList<String>();
        final File[] fileList = directory.listFiles();
        for (final File file : fileList) {
            if (file.isDirectory()) {
                retval.addAll(getResourcesFromDirectory(file));
            } else {
                try {
                    String fileName = "file:/" + file.getCanonicalPath();
                    fileName = PathTools.normalizeFilePath(fileName);
                    retval.add(fileName);
                } catch (final IOException e) {
                    Logger.getInstance(ResourceTools.class).logError(e.getMessage());
                }
            }
        }
        return retval;
    }

    public static List<String> getItemResources(String element) {
        final ArrayList<String> results = new ArrayList<String>();
        final File file = new File(element);
        if (!file.exists()) {
            logger.logWarning("Class path item \"" + element + "\" does not exist");
            return results;
        }
        if (file.isDirectory()) {
            results.addAll(getResourcesFromDirectory(file));
        } else {
            String path = file.getAbsolutePath();
            if (path.endsWith(".jar")) {
                results.addAll(getResourcesFromJarFile(file));
            } else {
                path = PathTools.normalizeFilePath(path);
                results.add(path);
            }
        }
        return results;
    }

    public String getResourcePath(String element) {
        List<String> resources = new ArrayList<>();
        Collection<String> classPathItems = getClassPathElements();
        for (String item : classPathItems) {
            Collection<String> itemResources = getItemResources(item);
            resources.addAll(itemResources);
        }
        return "";
    }

    private List<String> getFileItemResources(String itemPath) {
        List<String> itemResources = null;
        if (!fileResourcesMap.containsKey(itemPath)) {
            itemResources = getItemResources(itemPath);
            fileResourcesMap.put(itemPath, itemResources);
        } else {
            itemResources = fileResourcesMap.get(itemPath);
        }
        return itemResources;
    }

    public String findResourceFullPath(String resourcePath) {
        resourcePath = PathTools.normalizeFilePath(resourcePath);
        Collection<String> classPathItems = getClassPathElements();
        for (String classPathItem : classPathItems) {
            List<String> itemResources = getFileItemResources(classPathItem);
            for (String resourceItem : itemResources) {
                if (resourceItem.endsWith(resourcePath)) {
                    return resourceItem;
                }
            }
        }
        return null;
    }

    public static ImageIcon getResourceImageIcon(String resourcePath) {
        // 1. it's traditional way for single jar and for IDEA plugin.
        URL traditionalURL = ResourceTools.class.getClassLoader().getResource(resourcePath);
        if (traditionalURL != null) {
            ImageIcon imageIcon = new ImageIcon(traditionalURL);
            return imageIcon;
        }
        // 2. When resource is in other module and we are not in IDEA.
        String fullResourcePath = ResourceTools.getInstance().findResourceFullPath(resourcePath);
        URL imageURL = null;
        try {
            imageURL = new URL(fullResourcePath);
        } catch (Exception exception) {
        }
        ImageIcon imageIcon = new ImageIcon(imageURL);
        return imageIcon;
    }
}
