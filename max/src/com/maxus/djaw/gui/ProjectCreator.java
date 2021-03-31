package com.maxus.djaw.gui;

import com.maxus.djaw.DJaw;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;

import com.maxus.djaw.parse.DJWParser;
import com.maxus.djaw.parse.DJWParser.Portfolio;

import org.json.simple.JSONObject;
import java.io.IOException;
import java.util.Map;


public class ProjectCreator {
    private static final String path = System.getProperty("user.dir");
    private static FileWriter file;
    public static void main(String[] args){
        DJaw.DJawLogger.INFO("Loading DJaw ProjectCreator...", DJaw.log);
        DJaw.DJMessage("Loading ProjectCreator...", 0);
        throw new com.maxus.djaw.io.DeadEndException("DEAD_END_001", new Error());
    }
    @SuppressWarnings("unchecked")
    public static JSONObject ESObjDump(
            String type, String ID, String Description, String Name
    ){
        JSONObject obj = new JSONObject();
        JSONObject abilities = new JSONObject();

        abilities.put("abilityClass", null);
        abilities.put("creator", null);

        obj.put(type+"ID", ID);
        obj.put(type+"Description", Description);
        obj.put(type+"Name", Name);
        obj.put(type+"Abilities", abilities);

        return obj;
    }
    @SuppressWarnings("unchecked")
    public static JSONObject objDump(
        String name, String description, String creator, String ID,
        String packageName, String mainClass, String credits,
        String website, String version, String language
        )
    {
        JSONObject obj = new JSONObject();
        DJaw.DJawLogger.INFO("Dumping data into object...", DJaw.log);
        obj.put("normalSignature", "classical_unstable");
        obj.put("dunjerCache", false);
        obj.put("djawCache", false);
        obj.put("devjawCache", false);
        obj.put("standardCompiler", "djaw");
        obj.put("mainClass", mainClass);
        obj.put("exceptionClass", null);
        obj.put("modifyInnerCode", false);
        obj.put("package", packageName);
        obj.put("leaveSignature", false);
        obj.put("logConsole", false);
        obj.put("projectID", ID);
        obj.put("projectName", name);
        obj.put("projectDescription", description);
        obj.put("projectAuthor", creator);
        obj.put("projectCredits", credits);
        obj.put("projectLanguage", language);
        obj.put("projectWebsite", website);
        obj.put("projectVersion", version);
        return obj;
    }
    public static void dump(
            String name, String description, String creator, String ID,
            String packageName, String mainClass, String credits, String website, String version, String language,
            String filename
        ){
        JSONObject obj = objDump(name, description, creator, ID, packageName, mainClass, credits, website, version, language);
        DJaw.DJawLogger.INFO("Dumping object into... well another object", DJaw.log);
        try {
            File directory = createDirectory(path+"\\djaw\\projects\\"+filename+"\\");
            System.out.println(directory);
            File tmp = new File(directory, "data.dji");
            System.out.println(tmp.createNewFile());
            DJaw.DJMessage("Created a DJI File!", 0);
            file = new FileWriter(directory+"\\data.dji");
            file.write(obj.toJSONString());
            System.out.println(file);
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
    public static File createDirectory(String directoryPath) throws NullPointerException, IOException {
        File dir = new File(directoryPath);
        if (dir.exists()) {
            return dir;
        }
        if (dir.mkdirs()) {
            return dir;
        }
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }
    public static void createAnotherWindow(){
        DJaw.DJawLogger.INFO("Creating another window...", DJaw.log);
        String pathToIcon = "/com/maxus/djaw/gui/icon.png";
        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        DJaw.DJMessage("Loading GUI...", 0);

        //create frame
        JFrame frame = new JFrame("Create new DJaw project");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1200, 500);
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Note: ID should use '_' instead of spaces");
        JTextField name = new JTextField("Name(Test Project)", 20);
        JTextField description = new JTextField("Description(some description)", 60);
        JTextField creatorName = new JTextField("Creator(Maksim 'Maxuss' Petrov)", 30);
        JTextField id = new JTextField("id(project-id)", 16);
        JTextField packageName = new JTextField("package(com.example.djaw)", 40);
        JTextField mainClass = new JTextField("main class(MainClass)", 50);
        JTextField credits = new JTextField("credits(My friends)", 80);
        JTextField website = new JTextField("website(example.com)", 40);
        JTextField version = new JTextField("Version(1.0.0.0)", 40);
        JTextField language = new JTextField("Lang(en-US)", 10);
        panel.add(name);
        panel.add(language);
        panel.add(description);
        panel.add(creatorName);
        panel.add(id);
        panel.add(packageName);
        panel.add(mainClass);
        panel.add(credits);
        panel.add(website);
        panel.add(version);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JButton button = new JButton("Confirm, and create a project!");
        JButton button2 = new JButton("Cancel and exit");
        panel1.add(button);
        panel1.add(button2);
        panel2.add(label);
        button.addActionListener(e ->{
            String pname = name.getText();
            String pdesc = description.getText();
            String pcn = creatorName.getText();
            String pid = id.getText();
            String ppn = packageName.getText();
            String pmc = mainClass.getText();
            String pc = credits.getText();
            String pw = website.getText();
            String pv = version.getText();
            String pl = language.getText();
            dump(pname, pdesc, pcn, pid, ppn, pmc, pc, pw, pv, pl, pid);
            GUI.popup("Creation successful!", "You have successfully created a new\n project at '" + path + "\\djaw\\projects\\"+pid+"'! Check it out!");
        });
        button2.addActionListener(evt -> frame.dispose());
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, panel1);
        frame.getContentPane().add(BorderLayout.NORTH, panel2);
        frame.setVisible(true);
        DJaw.DJawLogger.INFO("Created another window!", DJaw.log);
    }
    public static void showAvailableProjects() throws NullPointerException {
        String pathToIcon = "/com/maxus/djaw/gui/icon.png";
        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        DJaw.DJMessage("Loading GUI...", 0);

        //create frame
        JFrame frame = new JFrame("Create new DJaw project");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        JPanel panel = new JPanel();
        JPanel _2 = new JPanel();
        StringBuilder found_projects = new StringBuilder(" ");
        System.out.println(found_projects);
        try {
            String[] projects = DJWParser.FindProjects();
            for (String project : projects) {
                found_projects.append(project).append(", ");
                JButton projectButton = new JButton(project);
                _2.add(projectButton);
                projectButton.addActionListener(e -> {
                    frame.dispose();
                    openFileConfig(project);
                });
            }
        } catch(NullPointerException e) {
            DJaw.DJMessage(e.toString(), 1);
            GUI.popup("ERROR","Projects not found! Create a new project, to see it!");
        }
        String we = "DJaw found these projects under current directory! Click a button to see config of project:\n ";
        JLabel label1 = new JLabel(we);
        panel.add(label1);
        JPanel _3 = new JPanel();
        JButton button = new JButton("OK");
        button.addActionListener(e -> frame.dispose());
        _3.add(button);
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, _2);
        frame.getContentPane().add(BorderLayout.SOUTH, _3);
        frame.setVisible(true);
    }

    public static void openFileConfig(String projectName){
        DJaw.DJawLogger.INFO("Parsing config...", DJaw.log);
        Map map = DJWParser.ConnectData();
        Portfolio data = (Portfolio) map.get(projectName);

        String pathToIcon = "/com/maxus/djaw/gui/icon.png";
        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        DJaw.DJMessage("Loading GUI...", 0);
        //create frame
        JFrame frame = new JFrame(projectName + " config");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 250);

        JPanel panel = new JPanel();
        String full = "<html><li>Name: "+data.name+"</li><li>ID: "+data.id+"</li><li>Version: "+data.ver+"</li><li>Description: "+data.description+"</li><li>Creator: "+data.author+"</li><li>Credits: "+data.credits+"</li><li>Language: "+data.language+"</li><li>Website: "+data.website+"</li><li>Standard Compiler: "+data.compiler+"</li><li>Main class:"+data.classname+"</li><li>Main Exceptions class:"+data.eClassname+"</li></html>";
        panel.add(new JLabel(full));
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setVisible(true);
    }
    public static void windowCCAP(){
        DJaw.DJawLogger.INFO("Creating class creator window", DJaw.log);
        String pathToIcon = "/com/maxus/djaw/gui/icon.png";
        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        DJaw.DJMessage("Loading GUI...", 0);

        //create frame
        JFrame frame = new JFrame("Create new DJaw Class");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel select = new JLabel("Select one of existing projects to generate class and package for");
        panel.add(select);
        try {
            String[] projects = DJWParser.FindProjects();
            for (String project : projects) {
                Portfolio prt = DJWParser.Data(project);
                JButton projectButton = new JButton(project);
                panel2.add(projectButton);
                String pack = "com."+prt.id+".djaw";
                projectButton.addActionListener(e -> {
                    frame.dispose();
                    createClassAndPackage(pack, prt.classname, prt.id);
                });
            }
        } catch(NullPointerException e) {
            DJaw.DJMessage(e.toString(), 1);
            GUI.popup("ERROR", "Projects not found! Create a new project, to see it!");
        }
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, panel2);
        frame.setVisible(true);
    }
    @SuppressWarnings("all")
    public static void createClassAndPackage(String packageName, String className, String projectID) throws com.maxus.djaw.io.IOException {
        DJaw.DJawLogger.INFO("Starting creating a class for project " + projectID, DJaw.log);
        String full = "\\src\\" + packageName.replace(".","\\");

        String SampleCode =  "package djaw.projects." + projectID + "." + packageName + ";\nimport com.maxus.djaw.DJaw;\n//TODO Auto-Generated file\npublic static class "+className+"{\n  public static void main(String[] args){\n    }\n}";
        DJaw.DJawLogger.INFO("Dumping following code: <" + SampleCode + ">", DJaw.log);
        try {
            File directory = createDirectory(path+"\\djaw\\projects\\"+projectID);
            System.out.println(directory);
            File directory1 = createDirectory(directory+full);
            System.out.println(directory);
            File tmp = new File(directory1, className + ".java");
            boolean a = tmp.createNewFile();
            System.out.println(a);
            DJaw.DJMessage("Created a JAVA CLASS File!", 0);
            file = new FileWriter(directory1+ "\\"+className+".java");
            file.write(SampleCode);
            System.out.println(file);
            GUI.popup("Done!","Successfully generated a class and package for "+projectID);
        } catch (IOException e) {
            throw new com.maxus.djaw.io.IOException(e.fillInStackTrace().getMessage(), e);
        } finally {

            try {
                file.flush();
                file.close();
                DJaw.DJawLogger.INFO("Finished creating class!", DJaw.log);
            } catch (IOException e) {
                throw new com.maxus.djaw.io.IOException(e.fillInStackTrace().getMessage(), e);
            }
        }
    }
}
