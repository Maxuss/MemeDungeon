package com.maxus.md;

import com.maxus.djaw.DJaw;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import static com.maxus.djaw.gui.ProjectCreator.createDirectory;


public class Backup {

    public static final String path = System.getProperty("user.dir");
    private static FileWriter file;
    public Backup(JSONObject data, String filename){
        hashCustom(data.toJSONString(), filename);
    }
    public static JSONObject getBackupData(String filename){
        JSONObject result;
        JSONParser parser = new JSONParser();
        try {
            String rpn = path + "\\md_data\\backups\\" + filename + ".bkup";
            System.out.println(rpn);
            Object obj = parser.parse(new FileReader(rpn));
            JSONObject file = (JSONObject) obj;
            String msg = "Loaded MD Backup!";
            DJaw.DJawLogger.INFO(msg, DJaw.log);
            DJaw.DJMessage(msg, 0);
            result = file;
        } catch (Exception error) {
            error.printStackTrace();
            result = new JSONObject();
        }
        return result;
    }
    public static String hashCustom(String str, String filename){
        String encodedString = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println(encodedString);
        try {
            File directory = createDirectory(path+"\\md_data\\backups\\");
            System.out.println(directory);
            File tmp = new File(directory, filename + ".bkup");
            boolean a = tmp.createNewFile();
            System.out.println(a);
            DJaw.DJMessage("Created a backup for player save file!", 0);
            file = new FileWriter(directory+ "\\"+filename+".bkup");
            file.write(encodedString);
            System.out.println(file);
            DJaw.DJawLogger.INFO("Successfully generated a backup file for player save!", DJaw.log);
        } catch (IOException e) {
            throw new com.maxus.djaw.io.IOException(e.fillInStackTrace().getMessage(), e);
        } finally {

            try {
                file.flush();
                file.close();
                DJaw.DJawLogger.INFO("Finished creating backup file!", DJaw.log);
            } catch (IOException e) {
                throw new com.maxus.djaw.io.IOException(e.fillInStackTrace().getMessage(), e);
            }
        }
        return encodedString;
    }
    public static JSONArray getLogData(){
        String pathTD = path + "\\md_data\\saves\\.ld";
        JSONArray result;
        JSONParser parser = new JSONParser();
        try {
            System.out.println(pathTD);
            Object obj = parser.parse(new FileReader(pathTD));
            JSONArray file = (JSONArray) obj;
            String msg = "Loaded MD Backup!";
            DJaw.DJawLogger.INFO(msg, DJaw.log);
            DJaw.DJMessage(msg, 0);
            result = file;
        } catch (Exception error) {
            error.printStackTrace();
            result = new JSONArray();
        }
        return result;
    }
}
