package com.maxus.djaw.parse;

import com.maxus.djaw.DJaw;
import com.maxus.djaw.gui.GUI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import static com.maxus.djaw.gui.ProjectCreator.createDirectory;


/*
includes simple json
 */

/**
 * Main class for parsing .dji data for projects
 * @author maxus
 */
public class DJWParser {
    private static final String path = System.getProperty("user.dir");
    private static FileWriter file;
    public static void main(String[] args){
        DJaw.DJMessage("Loading data from " + path, 0);
        createConfig();
    }
    public static JSONObject ParseDJI(String project_name) {
        DJaw.DJawLogger.INFO("Started parsing DJI of project " + project_name, DJaw.log);
        JSONObject result;
        JSONParser parser = new JSONParser();
        try {
            String filename = path + "\\djaw\\projects\\" + project_name + "\\data.dji";
            Object obj = parser.parse(new FileReader(filename));
            JSONObject file = (JSONObject) obj;
            String msg = "Loaded DJaw Project using data.dji. ProjectID: " + file.get("projectID").toString();
            DJaw.DJMessage(msg, 0);
            result = file;
        } catch (Exception error) {
            error.printStackTrace();
            result = new JSONObject();
        }
        return result;
    }
    @SuppressWarnings("unused")
    public static JSONObject CustomParseDJI(String filepath)
    {
        DJaw.DJawLogger.INFO("Started parsing DJI of file from path " + filepath, DJaw.log);
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filepath));
            JSONObject file = (JSONObject) obj;
            String msg = "Loaded DJaw DJI File from " + filepath;
            DJaw.DJMessage(msg, 9);
            return file;
        } catch (Exception error) {
            error.printStackTrace();
            return new JSONObject();
        }
    }
    /**
     * <h3>DJaw Outer</h3>
     * Class for storing DJaw Project Data that is basically just.<br>
     * Used by DJWParser.Outer() method
     * @see DJWParser
     * @author maxus
     *
     **/
    public static class Portfolio{
        public String name;
        public String id;
        public String ver;
        public String description;
        public String author;
        public String credits;
        public String language;
        public String website;
        public boolean[] cache = new boolean[3];
        public String compiler;
        public String classname;
        public String eClassname;
    }

    /**
     * Used for creating and filling Data object
     * @param filename Name of project
     *
     */
    public static Portfolio Data(String filename){
        // creating all the needed objects
        Portfolio OD = new Portfolio();
        JSONObject File = ParseDJI(filename);
        // writing data to objects
        OD.name = (String) File.get("projectName");
        OD.author = (String) File.get("projectAuthor");
        OD.credits = (String) File.get("projectCredits");
        OD.id = (String) File.get("projectID");
        OD.ver = (String) File.get("projectVersion");
        OD.description = (String) File.get("projectDescription");
        OD.website = (String) File.get("projectWebsite");
        OD.language = (String) File.get("projectLanguage");
        OD.cache[0] = (Boolean) File.get("djawCache");
        OD.cache[1] = (Boolean) File.get("devjawCache");
        OD.cache[2] = (Boolean) File.get("dunjerCache");
        OD.compiler = (String) File.get("standardCompiler");
        OD.classname = (String) File.get("mainClass");
        OD.eClassname = (String) File.get("exceptionClass");
        // returning
        return OD;
    }
    /**
     * Used for finding all the DJaw projects in projects folder
     * @see DJWParser
     */
    public static String[] FindProjects(){
        File[] directories = new File(path + "\\djaw\\projects\\").listFiles(File::isDirectory);
        String[] dirs = new String[directories.length];
        System.out.println(directories.length);
        for(int i = 0; i < directories.length ; i++){
            dirs[i] = directories[i].getName();
        }
        String msg = "Found following projects " + Arrays.toString(dirs);
        DJaw.DJMessage(msg, 0);
        return dirs;
    }
    /**
     * Used for connecting data together. Gets through all the projects and opens all the project data.
     * @see DJWParser
     * @author maxus
     */
    public static Map ConnectData(){
        String[] projects = FindProjects();
        Map<String, Portfolio> database = new TreeMap<>();

        // parses all the files
        for (String current : projects) {
            System.out.println(current);
            DJaw.DJMessage(current, 0);
            database.put(current, Data(current));
        }
        return database;
    }
    @SuppressWarnings("unchecked")
    public static void createConfig() {
         try {
             JSONObject obj = new JSONObject();
             obj.put("normalMode", "windowed");
             obj.put("_comment", "FOV in buttons, OR use 'sizeX' and 'sizeY' respectively.");
             obj.put("mappingFOVX", 4);
             obj.put("mappingFOVY", 4);
             File directory = createDirectory(path + "\\djaw\\");
             System.out.println(directory);
             File tmp = new File(directory, "config.dji");
             if (!tmp.exists()) {
                 try {
                     boolean a = tmp.createNewFile();
                     System.out.println(a);
                     DJaw.DJMessage("Created a JAVA CLASS File!", 0);
                     file = new FileWriter(directory + "\\config.dji");
                     file.write(obj.toJSONString());
                     System.out.println(file);
                     GUI.popup("Done!", "Successfully generated a DJI Config!");

                 } catch (IOException e) {
                     e.printStackTrace();

                 } finally {

                     try {
                         file.flush();
                         file.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             }
         } catch(IOException er){
             er.printStackTrace();
         }
    }
    public static JSONObject parseConfig()
    {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(path + "\\djaw\\config.dji"));
            JSONObject file = (JSONObject) obj;
            String msg = "Loaded DJaw Configurations file!";
            DJaw.DJMessage(msg, 9);
            return file;
        } catch (Exception error) {
            error.printStackTrace();
            return new JSONObject();
        }
    }
}
