package com.yeremeyev.java.core.logger;

public class Logger {
    private static final String DEBUG_PREFIX = "DEBUG";
    private static final String WARNING_PREFIX = "WARNING";
    private static final String INFO_PREFIX = "INFO";
    private static final String ERROR_PREFIX = "ERROR";

    private String className;
    private boolean isDebugVisible = false;
    private boolean isWarningVisible = false;
    private boolean isInfoVisible = true;
    private boolean isErrorVisible = true;

    protected Logger(String className) {
        this.className = className;
    }

    public static Logger getInstance(Class clazz) {
        return new Logger(clazz.getCanonicalName());
    }

    protected void logMessage(String type, String message) {
        String titleTemplate = "%s : %s";
        String title = String.format(titleTemplate, type, className);
        System.out.println(title);
        System.out.println(message);
    }

    public void logDebug(String message) {
        if (isDebugVisible) {
            logMessage(DEBUG_PREFIX, message);
        }
    }

    public void logWarning(String message) {
        if (isWarningVisible) {
            logMessage(WARNING_PREFIX, message);
        }
    }

    public void logInfo(String message) {
        if (isInfoVisible) {
            logMessage(INFO_PREFIX, message);
        }
    }

    public void logError(String message) {
        if (isErrorVisible) {
            logMessage(ERROR_PREFIX, message);
        }
    }
}
