package com.maxus.djaw.gui;

import com.maxus.djaw.DJaw;
import com.maxus.djaw.tilesheets.DJawMapping;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GUI {
    public static void main(String[] args){
        DJaw.DJMessage("GUI SETUP", 0);
        createGUI();
    }

    public static void createGUI(){
        String pathToIcon = "/com/maxus/djaw/gui/icon.png";
        System.out.println(pathToIcon);

        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        DJaw.DJMessage("Loading GUI...", 0);

        //create frame
        JFrame frame = new JFrame("DJaw Menu");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 250);

        JPanel panel = new JPanel();
        JLabel text1 = new JLabel("You can create a simple project formation! But you have to use coding skills for creating a serious project. ");
        JLabel text2 = new JLabel("Please check our documentation to find out on how everything works. Big thanks!");
        panel.add(text1);
        panel.add(text2);
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JButton button = new JButton("Get me to documentation");
        panel2.add(button);
        JButton button1 = new JButton("I want to config a small project!");
        panel2.add(button1);
        JButton button2 = new JButton("View project configs");
        panel2.add(button2);
        JButton button3 = new JButton("Generate class for a project");
        panel3.add(button3);
        JButton button4 = new JButton("View a custom generated map with some assets");
        panel3.add(button4);
        button.addActionListener(e -> {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        try {
                            Desktop.getDesktop().browse(new URI("https://github.com/Maxuss/djaw/blob/main/README.md"));
                        } catch (IOException | URISyntaxException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
        button1.addActionListener(a -> ProjectCreator.createAnotherWindow());
        button2.addActionListener(event -> ProjectCreator.showAvailableProjects());
        button3.addActionListener(evt -> ProjectCreator.windowCCAP());
        button4.addActionListener(eventa -> {
                String[] tilePaths = new String[]{
                        "/tile1.png", "/tile2.png", "/tile3.png", "/tile4.png", "/tile5.png", "/tile6.png"
                };
            JFrame framer = new JFrame();
            try {
                framer = DJawMapping.SetupMapGUI(tilePaths);
            } catch (IOException e) {
                DJaw.DJMessage(e.toString(), 2);
            }
            framer.setVisible(true);
        });

        // compiling
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, panel2);
        frame.getContentPane().add(BorderLayout.NORTH, panel3);
        frame.setVisible(true);
    }
    public static void popup(String title, String message){
        final Runnable runnable =
                (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
        if (runnable != null) runnable.run();
        String pathToIcon = "/com/maxus/djaw/gui/icon.png";
        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);



        JLabel text = new JLabel(message);
        JDialog popup = new JDialog();
        JButton button = new JButton("OK");
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        button.addActionListener(evt -> popup.dispose());

        popup.setTitle(title);
        panel2.add(text);
        panel.add(button);
        popup.getContentPane().add(BorderLayout.SOUTH, panel);
        popup.getContentPane().add(BorderLayout.CENTER, panel2);
        popup.setIconImage(icon.getImage());
        popup.setVisible(true);
        popup.setSize(700, 100);
        popup.setLocation(500, 500);
        popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}