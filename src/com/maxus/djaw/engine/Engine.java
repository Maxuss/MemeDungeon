package com.maxus.djaw.engine;

import com.maxus.djaw.DJaw;
import com.maxus.djaw.gui.ProjectCreator;
import org.json.simple.JSONObject;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static com.maxus.djaw.gui.ProjectCreator.createDirectory;



/**
 * <h1>DJaw Engine</h1>
 * <p>Main class for Djaw engine.</p>
 * <h2>Methods:</h2>
 * <li>Start() - starts up engine</li>
 * <li>Stop() - stops the engine and closes the window</li>
 * <h2>Subclasses:</h2>
 *
 * <h3> Enemy:</h3>
 * <p>Main enemy class. Can create mobs and more.<br></p>
 * <h4>Methods:</h4>
 * <li>Create() - creates an enemy</li>
 * <li>Disband() - destroys an enemy</li>
 * <li>Modify() - changes data of enemy</li>
 * <li>Load() - loads an item from file</li>
 *
 * <h3>Item:</h3>
 * <p>Main items class. Can create items and stuff.<br></p>
 * <h4>Methods:</h4>
 * <li>Create() - creates an item</li>
 * <li>Disband() - deletes an item</li>
 * <li>Modify() - modifies data of existing item</li>
 * <li>Load() - loads an item from file</li>
 *
 * <h3>Creation:</h3>
 * <p>Main buildings/creations class. Can create blocks and stuff i think.<br></p>
 * <h4>Methods:</h4>
 * <li>Create() - creates a block</li>
 * <li>Disband() - deletes a block</li>
 * <li>Modify() - modifies data of existing block</li>
 * <li>Load() - loads an item from file</li>
 *
 * <h3>Abilities</h3>
 * <p>Class that gives more interaction to items/buildings stuff</p>
 * <h4>Methods:</h4>
 * <li>Create() - creates an ability</li>
 * <li>Load() - loads an ability from file, and applies it to some mob/building</li>
 * <li>Delete() - deletes an ability</li>
 * <li>Modify() - modifies an ability</li>
 *
 * @see com.maxus.djaw.DJaw
 * @see com.maxus.djaw.gui.GUI
 * @see com.maxus.djaw.gui.ProjectCreator
 * @author maxus
 **/
public class Engine {

    public static final String path = System.getProperty("user.dir");
    public static void main(String[] args){
        DJaw.DJMessage("Pre-initializing engine", 0);
    }

    /**
     * <h2>DJaw Engine</h2>
     * Starts up the DJaw Engine
     * @see Engine
     * @return GUI
     **/
    @SuppressWarnings("unused")
    public static void Start(JFrame frame){
        frame.setVisible(true);
        DJaw.DJMessage("Starting engine", 0);
    }
    /**
     * <h2>DJaw Engine</h2>
     * Stops the engine
     * **/
    @SuppressWarnings("unused")
    public static void Stop(JFrame frame){
        frame.dispose();
        DJaw.DJMessage("Stopping the engine...", 1);
    }
    /**
     * <h2>DJaw Engine</h2>
     * Main class for creations of any kind of stuff
     * Currently only supports 'item', 'build', 'mob' types of items
     * **/
    @SuppressWarnings("unused")
    public static class Creation{
        public static void main(String[] args){
            Creation.Create("test", "Item Test", "TEst ITEM", "item");
            JSONObject obj = ProjectCreator.ESObjDump("build", "test","test", "creationDesk");
            Creation.Modify("test-creation", obj, "mob");
        }
        private static FileWriter file;

        /**
         * <h2>DJawEngine.Creation</h2>
         * Creates item in <path>\djaw\creates</path> folder.
         * <br>Creation will have a .dji file named with CreateID. CreateID <b>DOES NOT ALLOW SPACES</b>
         * @param CreateID - ID of creation. Does not allow spacebars.
         * @param CreateName - Name of creation. Allows spacebars.
         * @param CreateDescription - Description of creation. Allows spacebars.
         * @param type supports 'mob', 'item', 'build'
         **/

        public static void Create(String CreateID, String CreateName, String CreateDescription, String type)
        {
            // create a new json obj
            JSONObject obj = ProjectCreator.ESObjDump(type, CreateID,CreateDescription, CreateName);
            try {
                File directory = createDirectory(path+"\\djaw\\"+type+"s\\");
                System.out.println(directory);
                File tmp = new File(directory, CreateID + ".dji");
                boolean a = tmp.createNewFile();
                System.out.println(a);
                DJaw.DJMessage("Created a DJI File!", 0);
                file = new FileWriter(directory+ "\\"+CreateID+".dji");
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
        /**
         * <h2>DJawEngine.Creation</h2>
         * Deletes something in <path>\djaw\creates</path> folder.
         * @param CreateID  ID creation. Does not allow spacebars.
         * @param type supports 'mob', 'item', 'build'
         **/
        public static void Disband(String CreateID, String type){
            try {
                File directory = createDirectory(path+"\\djaw\\"+type+"s\\");
                System.out.println(directory);
                File tmp = new File(directory, CreateID + ".dji");
                boolean a = tmp.delete();
                System.out.println(a);
                DJaw.DJMessage("Deleted a DJI File!", 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         * <h2>DJawEngine.Creation</h2>
         * Modifies enemy data in <path>\djaw\create</path> folder.
         * @param CreateID  ID of creation. Does not allow spacebars.
         * @param Modificated  JSONObject with new data.
         * @param type supports 'mob', 'item', 'build'
         **/
        public static void Modify(String CreateID, JSONObject Modificated, String type){
            try {
                File directory = createDirectory(path+"\\djaw\\"+type+"s");
                System.out.println(directory);
                DJaw.DJMessage("Created a DJI File!", 0);
                file = new FileWriter(directory+"\\"+CreateID+".dji");
                file.write(Modificated.toJSONString());
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
    }


}
