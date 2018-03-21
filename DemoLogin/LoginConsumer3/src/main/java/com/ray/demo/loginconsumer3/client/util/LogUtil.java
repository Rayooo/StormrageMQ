package com.ray.demo.loginconsumer3.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private LogUtil(){}

    public static void logInfo(String content){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[2].getClassName();
        Logger logger = LoggerFactory.getLogger(name);
        logger.info(content);
    }

    public static void logDebug(String content){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[2].getClassName();
        Logger logger = LoggerFactory.getLogger(name);
        logger.debug(content);
    }

    public static void logWarning(String content){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[2].getClassName();
        Logger logger = LoggerFactory.getLogger(name);
        logger.warn(content);
    }

    public static void logError(String content){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[2].getClassName();
        Logger logger = LoggerFactory.getLogger(name);
        logger.error(content);
    }

}
