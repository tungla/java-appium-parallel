package com.kroger.qa.automation.appium.framework.utils;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log {

    private static final Logger Log = LogManager.getLogger(Log.class.getName());

    private enum LogLevel {FATAL, ERROR, WARN, INFO, DEBUG, TRACE}

    static {

        LogLevel level;

        try { level = LogLevel.valueOf(System.getProperty("log", "info").toUpperCase()); }
        catch (IllegalArgumentException ignored) { level = LogLevel.INFO; }

        switch (level) {
            case FATAL:
                Configurator.setRootLevel(Level.FATAL);
                break;
            case ERROR:
                Configurator.setRootLevel(Level.ERROR);
                break;
            case WARN:
                Configurator.setRootLevel(Level.WARN);
                break;
            case DEBUG:
                Configurator.setRootLevel(Level.DEBUG);
                break;
            case TRACE:
                Configurator.setRootLevel(Level.TRACE);
                break;
            default:
                Configurator.setRootLevel(Level.INFO);
        }

        BasicConfigurator.configure();
    }

    public static void fatal(String message) { Log.fatal(message); }
    public static void error(String message) { Log.error(message); }
    public static void warn(String message) { Log.warn(message); }
    public static void info(String message) { Log.info(message); }

    public static void debug(String message) { Log.debug(message); }
    public static void trace(String message) { Log.trace(message); }
}