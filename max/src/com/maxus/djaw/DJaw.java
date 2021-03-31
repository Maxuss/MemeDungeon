package com.maxus.djaw;

import com.maxus.djaw.gui.GUI;
import com.maxus.djaw.parse.DJWParser;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.maxus.djaw.gui.ProjectCreator.createDirectory;

// main class
public class DJaw {
    public static final Logger log = DJawLogger.startLog();
    public static final String path = System.getProperty("user.dir");
    // starting
    public static void main(String[] args)  {
        DJMessage("Loading DJAW...", 0);
        DJawLogger.startLog();
        Condition("customCondition");
        GUI.createGUI();
        DJWParser.ConnectData();
        DJWParser.createConfig();
        DJawLogger.INFO("Starting DJaw!", log);
    }

    /**
    method for debug messages
     0 for debug,
     1 for warn,
     2 for error,
     3 for fatal
    */
    @SuppressWarnings("unused")
    public static void DJMessage(String message, int type /* 0 for debug, 1 for warn, 2 for error, 3 for fatal*/){
        final String reset = "\u001B[0m";
        final String black = "\u001B[30m";
        final String red = "\u001B[31m";
        final String green = "\u001B[32m";
        final String yellow = "\u001B[33m";
        final String blue = "\u001B[34m";
        final String purple = "\u001B[35m";
        final String cyan = "\u001B[36m";
        final String white = "\u001B[37m";
        final String msg_returner;
        switch (type) {
            case 0:
                msg_returner = cyan + "[DJAW] -DEBUG- > " + message + reset;
                break;
            case 1:
                msg_returner = yellow + "[DJAW] -WARNING- > " + message + reset;
                break;
            case 2:
                msg_returner = purple + "[DJAW] -ERROR- > " + message + reset;
                break;
            case 3:
                msg_returner = red + "[DJAW] -FATAL_ERROR- > " + message + reset;
                break;
            default:
                msg_returner = yellow + "[DJAW] > " + message + reset;
                break;
        }
        System.out.println(msg_returner);

    }
    /**
     * Executes certain codes on different conditions<br>
     * Mainly made for project's use
     * @param condition - [str]condition
     * @see DJaw
     * @author maxus
     **/
    public static void Condition(String condition) {
        switch(condition){
            case "guiCreated":
                // on gui creation
            case "windowClosed":
                // on window close
            case "onLoad":
                // on djaw load
            case "onParsing":
                // on parsing a .dji file
            case "onFileCreation":
                // on creating a file
            case "customCondition":
                // custom condition
            default:
                // do nothing
        }
    }

    public static class DJawLogger {
        public static Logger startLog(){
            long timestamp = System.currentTimeMillis() / 1000;
            Logger logger = Logger.getLogger("DJawLogger");
            FileHandler fh;

            try {
                createDirectory(path+"\\djaw\\logs\\");
                File tmp = new File(path+"\\djaw\\logs\\", timestamp+"_log.txt");
                System.out.println(tmp.createNewFile());
                fh = new FileHandler(path+"\\djaw\\logs\\"+timestamp+"_log.txt");
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
                logger.info("--__--__--__--__--__--__--__--__--__--__--");
                logger.info("Woops! Looks like something bad happened!");
                logger.info("--__--__--__--__--__--__--__--__--__--__--");

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                try {
                    throw new com.maxus.djaw.io.IOException("UNDEFINED<IOException>", e);
                } catch(com.maxus.djaw.io.IOException er){
                    er.printStackTrace();
                }
            }

            logger.info("Starting logging session...");
            return logger;
        }

        public static void INFO(String message, Logger logger){
            logger.info(message);
        }

        public static void WARN(String message, Logger logger){
            logger.warning(message);
        }

        public static void ERROR(String message, Logger logger){
            logger.severe(message);
        }

        public static void FINE(String message, Logger logger){
            logger.fine(message);
        }
    }

}
