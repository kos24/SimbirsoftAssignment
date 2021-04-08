package com.htmlparser.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {

    static Logger logger;
    public FileHandler handler;
    SimpleFormatter simpleFormatter;

    private MyLogger() throws IOException {
        logger = Logger.getLogger(MyLogger.class.getName());
        handler = new FileHandler("wordCount.log",true);
        simpleFormatter = new SimpleFormatter();
        handler.setFormatter(simpleFormatter);
        logger.addHandler(handler);
    }

    private static Logger getLogger(){
        if (logger == null) {
            try {
                new MyLogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    public static void log(Level level, String message) {
        MyLogger.getLogger().log(level,message);
    }
}
