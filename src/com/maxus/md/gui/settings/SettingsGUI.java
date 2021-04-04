package com.maxus.md.gui.settings;

import com.maxus.djaw.DJaw;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class SettingsGUI {
    public static final String path = System.getProperty("user.dir");
    public static void main(String[] args){

    }
    public static void Start(){
        Settings settings = new Settings();
    }
    public static class Settings {
        public boolean fullscreen;
        public boolean rememberUsers;
        public boolean limitPasswordTries;
        public int maxPasswordTries;
        public JSONObject knownUsers;
        public Settings(){
            DJaw.DJawLogger.INFO("MD > Started parsing Settings!", DJaw.log);
            JSONObject result;
            org.json.simple.parser.JSONParser parser = new JSONParser();
            try {
                String filename = path + "\\md_data\\configs\\main_settings.json";
                Object obj = parser.parse(new FileReader(filename));
                JSONObject file = (JSONObject) obj;
                String msg = "Loaded MD Settings";
                DJaw.DJMessage(msg, 0);
                result = file;
            } catch (Exception error) {
                error.printStackTrace();
                result = new JSONObject();
            }
            fullscreen = (boolean) result.get("fullscreen");
            rememberUsers = (boolean) result.get("rememberUsers");
            limitPasswordTries = (boolean) result.get("limitPasswordTries");
            maxPasswordTries = (int) result.get("maxPasswordTries");
            knownUsers = (JSONObject) result.get("knownUsers");
        }

        public JSONObject getKnownUser(String UserNum){ return (JSONObject) knownUsers.get(UserNum); }
        public String[] getAllUserNums(){ return (String[]) knownUsers.keySet().toArray(); }
    }
}
