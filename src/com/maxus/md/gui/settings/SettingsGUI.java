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
        /* Should look something like that:
                 UT
        LUP     CUP     RUP

        LCP     CCP     RCP

        LBP     CBP     RBP
                BP
         */

        // panels
        // upper panel
        JPanel upper = new JPanel();

        // left panels
        JPanel leftPanel = new JPanel();
        JPanel leftUpperPanel = new JPanel();
        JPanel leftCentralPanel = new JPanel();
        JPanel leftBottomPanel = new JPanel();

        // central panels
        JPanel centralPanel = new JPanel();
        JPanel centralUpperPanel = new JPanel();
        JPanel centralCentralPanel = new JPanel();
        JPanel centralBottomPanel = new JPanel();

        // right panels
        JPanel rightPanel = new JPanel();
        JPanel rightUpperPanel = new JPanel();
        JPanel rightCentralPanel = new JPanel();
        JPanel rightBottomPanel = new JPanel();

        // text labels
        JLabel sTXT = new JLabel("Settings");

        // buttons

        JButton lupTXT = new JButton("Fullscreen Mode");
        JButton lcpTXT = new JButton("Remember users");
        JButton lbpTXT = new JButton("Language");

        JButton cupTXT = new JButton("filler");
        JButton ccpTXT = new JButton("filler");
        JButton cbpTXT = new JButton("filler");

        JButton rupTXT = new JButton("filler");
        JButton rcpTXT = new JButton("filler");
        JButton rbpTXT = new JButton("filler");

        // adding

        // elements
        upper.add(sTXT);

        leftUpperPanel.add(lupTXT);
        leftCentralPanel.add(lcpTXT);
        leftBottomPanel.add(lbpTXT);

        centralUpperPanel.add(cupTXT);
        centralCentralPanel.add(ccpTXT);
        centralBottomPanel.add(cbpTXT);

        rightUpperPanel.add(rupTXT);
        rightCentralPanel.add(rcpTXT);
        rightBottomPanel.add(rbpTXT);

        // panels

        leftPanel.add(leftUpperPanel, BorderLayout.NORTH);
        leftPanel.add(leftCentralPanel, BorderLayout.CENTER);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        centralPanel.add(centralUpperPanel, BorderLayout.NORTH);
        centralPanel.add(centralCentralPanel, BorderLayout.CENTER);
        centralPanel.add(centralBottomPanel, BorderLayout.SOUTH);

        rightPanel.add(rightUpperPanel, BorderLayout.NORTH);
        rightPanel.add(rightCentralPanel, BorderLayout.CENTER);
        rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);

        // frame

        frame.getContentPane().add(BorderLayout.PAGE_START, leftPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centralPanel);
        frame.getContentPane().add(BorderLayout.PAGE_END, rightPanel);
        frame.getContentPane().add(BorderLayout.LINE_END, upper);

        // frame configs

        String pathToIcon = "/com/maxus/md/logging/icon.png";
        System.out.println(pathToIcon);

        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
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
