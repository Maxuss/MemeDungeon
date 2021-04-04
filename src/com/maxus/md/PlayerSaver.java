package com.maxus.md;

import com.maxus.djaw.DJaw;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;

import static com.maxus.djaw.gui.ProjectCreator.createDirectory;
public class PlayerSaver {
    private static FileWriter file;
    public static final long serialUUID = 9223372036854775807L;
    public static final String path = System.getProperty("user.dir");
    public static void main(String[] args) throws IOException {
        JSONObject dat = getPlayerData("test");
        __PLAYERDATA__ data = new __PLAYERDATA__(dat);
        data.aVoid();
    }

    public static JSONObject getPlayerData(String player_name){
        createProfile("test", "testy TEST");
        JSONObject result;
        JSONParser parser = new JSONParser();
        try {
            String rpn;
            if (player_name.toLowerCase().contains("test")){
                rpn = path + "\\md_data\\saves\\test.save";
            }else{
                rpn = path + "\\md_data\\saves\\" + player_name.toLowerCase() + ".save";
            }
            System.out.println(rpn);
            Object obj = parser.parse(new FileReader(rpn));
            JSONObject file = (JSONObject) obj;
            String msg = "Loaded MD Save using .save! PlayerName: " + file.get("playerName").toString();
            DJaw.DJawLogger.INFO(msg, DJaw.log);
            DJaw.DJMessage(msg, 0);
            result = file;
        } catch (Exception error) {
            error.printStackTrace();
            result = new JSONObject();
        }
        return result;
    }
    @SuppressWarnings("unused")
    public static class __PLAYERDATA__ {
        public static JSONObject dat;
        public String name;
        public long lastLogin;
        public long health;
        public long defence;
        public JSONArray inventory;
        public __PLAYERDATA__(JSONObject data){
            dat = data;
            System.out.println(data.isEmpty());
            name = (String) dat.get("playerName");
            lastLogin = (long) dat.get("lastLogin");
            health = (long) dat.get("maxHealth");
            defence = (long) dat.get("defence");
            inventory = (JSONArray) dat.get("inventory");
        }
        public void aVoid(){
            try {
                System.out.println(inventory.get(1));
            } catch(IndexOutOfBoundsException e){
                throw new com.maxus.djaw.io.DeadEndException(e.getMessage(), e);
            }
        }
    }
    @SuppressWarnings("unchecked")
    public static void createProfile(String name, String fullName){
        JSONObject standard = new JSONObject();
        JSONArray items = new JSONArray();
        standard.put("playerName", fullName);
        standard.put("lastLogin", Calendar.getInstance().getTimeInMillis());
        standard.put("maxHealth", 100);
        standard.put("defence", 0);
        standard.put("inventory", items);
        DJaw.DJawLogger.INFO("Creating new player file save", DJaw.log);
        try {
            File directory = createDirectory(path+"\\md_data\\saves\\");
            System.out.println(directory);
            File tmp = new File(directory, name + ".save");
            boolean a = tmp.createNewFile();
            System.out.println(a);
            DJaw.DJMessage("Created a .save player save file!", 0);
            file = new FileWriter(directory+ "\\"+name+".save");
            file.write(standard.toJSONString());
            System.out.println(file);
            DJaw.DJawLogger.INFO("Successfully generated a save file for player save!", DJaw.log);
        } catch (IOException e) {
            throw new com.maxus.djaw.io.IOException(e.fillInStackTrace().getMessage(), e);
        } finally {

            try {
                file.flush();
                file.close();
                DJaw.DJawLogger.INFO("Finished creating save file!", DJaw.log);
            } catch (IOException e) {
                throw new com.maxus.djaw.io.IOException(e.fillInStackTrace().getMessage(), e);
            }
        }
    }
    public static String hashData(String originalData, String filename){
        String encodedString = Base64.getEncoder().encodeToString(originalData.getBytes());
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
}
