package com.maxus.md.gui.settings;

import com.maxus.djaw.DJaw;
import com.maxus.djaw.gui.GUI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.net.URL;

public class SettingsGUI {
    public static final String path = System.getProperty("user.dir");
    public static void main(String[] args){
        CreateSettingsGUI();
    }
    public static void CreateSettingsGUI(){
        Settings settings = new Settings();
        System.out.println(settings.maxPasswordTries);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("MD Settings");
        JPanel leftPanel = new JPanel();
        JPanel centralPanel = new JPanel();
        String[] screens = {"Fullscreen", "Windowed"};
        JComboBox<String> lup = new JComboBox<>(screens);
        JLabel lup1 = new JLabel("Mode:");
        String[] users = {"ON", "OFF"};
        JComboBox<String> lcp = new JComboBox<>(users);
        JLabel lcp1 = new JLabel("Remember users:");
        String[] languages = {"English", "Русский"};
        JComboBox<String> lbp = new JComboBox<>(languages);
        JLabel lbp1 = new JLabel("Language:");
        String[] choices = {"Unimaginable", "Very high", "High", "Normal", "Low", "Super Low", "My PC Is A Potato"};
        JComboBox<String> cup = new JComboBox<>(choices);
        JLabel cup1 = new JLabel("Graphics:");
        String[] ch2 = {"Dark", "Light"};
        JComboBox<String> ccp = new JComboBox<>(ch2);
        JLabel ccp1 = new JLabel("Theme:");
        JTextField cbp = new JTextField(10);
        JLabel cbp1 = new JLabel("Name:");
        JTextField rup = new JTextField(11);
        JLabel rup1 = new JLabel("Secret code:");
        JButton bottom = new JButton("Send!");
        JPanel btm = new JPanel();
        btm.add(bottom);
        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        JPanel pan3 = new JPanel();
        JPanel pan4 = new JPanel();
        JPanel pan5 = new JPanel();
        JPanel pan6 = new JPanel();
        JPanel pan7 = new JPanel();
        pan1.add(lup1);
        pan1.add(lup);
        pan2.add(lcp1);
        pan2.add(lcp);
        pan3.add(lbp1);
        pan3.add(lbp);
        pan4.add(cup1);
        pan4.add(cup);
        pan5.add(ccp1);
        pan5.add(ccp);
        pan6.add(cbp1);
        pan6.add(cbp);
        pan7.add(rup1);
        pan7.add(rup);
        JPanel leftM = new JPanel();
        leftM.add(pan1, BorderLayout.NORTH);
        leftM.add(pan2, BorderLayout.CENTER);
        leftM.add(pan3, BorderLayout.SOUTH);
        JPanel cenM = new JPanel();
        cenM.add(pan4, BorderLayout.NORTH);
        cenM.add(pan5, BorderLayout.CENTER);
        cenM.add(pan6, BorderLayout.SOUTH);
        leftPanel.add(leftM, BorderLayout.NORTH);
        centralPanel.add(cenM, BorderLayout.CENTER);
        centralPanel.add(pan7, BorderLayout.SOUTH);
        frame.getContentPane().add(BorderLayout.NORTH, leftPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centralPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, btm);
        String pathToIcon = "/com/maxus/md/logging/icon.png";
        System.out.println(pathToIcon);
        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 230);
        frame.setVisible(true);
    }
    @SuppressWarnings("unused")
    public static class Settings {
        public boolean fullscreen;
        public boolean rememberUsers;
        public boolean limitPasswordTries;
        public long maxPasswordTries;
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
            maxPasswordTries = (long) result.get("maxPasswordTries");
            knownUsers = (JSONObject) result.get("knownUsers");
        }

        public JSONObject getKnownUser(String UserNum){ return (JSONObject) knownUsers.get(UserNum); }
        public String[] getAllUserNums(){ return (String[]) knownUsers.keySet().toArray(); }
    }
}
