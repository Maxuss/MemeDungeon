package com.maxus.md.logging;

import com.maxus.djaw.DJaw;
import com.maxus.djaw.gui.GUI;
import com.maxus.md.Backup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AccountManager {
    public static final String path = System.getProperty("user.dir");
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        CreatePromptUI();
    }

    public static void CreatePromptUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JSONArray logData = Backup.getLogData();
        JSONObject LD = (JSONObject) logData.get(0);
        JSONObject AU = (JSONObject) LD.get("allowed_users");
        JSONObject Allowed = (JSONObject) AU.get("1");
        String allowedUsername = (String) Allowed.get("username");
        String allowedPassword = (String) Allowed.get("password");


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        String pathToIcon = "/com/maxus/md/logging/icon.png";
        System.out.println(pathToIcon);

        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        DJaw.DJMessage("Loading MD...", 0);

        JFrame frame = new JFrame("Login to MemeDungeon BETA");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        String pathToImg = "/com/maxus/md/logging/thumbnail.png";
        URL img = GUI.class.getResource(pathToImg);
        ImageIcon IMGIcon = new ImageIcon(img);
        JLabel image = new JLabel(IMGIcon);
        JPanel iPanel = new JPanel();
        JPanel pPanel = new JPanel();
        JPanel nPanel = new JPanel();
        JPanel bPanel = new JPanel();
        JLabel nLabel = new JLabel("Username:");
        JLabel pLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JTextField nameField = new JTextField(20);
        JButton send = new JButton("Log in!");
        JPanel fPanel = new JPanel();

        send.addActionListener(e -> {
            String pInput = new String(passwordField.getPassword());
            String nInput = nameField.getText();

            if(pInput.equals(allowedPassword) & nInput.equals(allowedUsername)){
                GUI.popup("Success!", "Login successful!");
            } else {
                GUI.popup("Error!", "Wrong username or password!");
            }
        });
        pPanel.add(pLabel);
        pPanel.add(passwordField);
        nPanel.add(nLabel);
        nPanel.add(nameField);
        bPanel.add(send);
        fPanel.add(pPanel);
        fPanel.add(nPanel);
        iPanel.add(image);

        frame.getContentPane().add(BorderLayout.CENTER, fPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, bPanel);
        frame.getContentPane().add(BorderLayout.NORTH, iPanel);

        frame.setVisible(true);
    }
}
