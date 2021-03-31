package com.maxus.djaw.tilesheets;

import com.maxus.djaw.DJaw;

import com.maxus.djaw.gui.GUI;
import com.maxus.djaw.parse.DJWParser;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import static com.maxus.djaw.gui.ProjectCreator.createDirectory;


@SuppressWarnings("unused")
public class DJawMapping {
    public static final String path = System.getProperty("user.dir");
    public static void main(String[] args) { }
    /**
     * Main class for mapping
     * @author maxus
    **/
    @SuppressWarnings("unused")
    public static class TileMap{
        // main variables
        public static int sizeX;
        public static int sizeY;
        public static boolean resizable;
        public static String texture;
        public static boolean includeHitboxes;
        public static JSONObject hitboxes;
        public static String id;
        public static JSONObject Structures;
        public static Map OccupiedTiles;
        public static Map tiles;

        // main methods
        public static int getSizeX(){ return sizeX; }
        public static int getSizeY(){ return sizeY; }
        public static boolean isResizable(){ return resizable; }
        public static String getTexture(){ return texture; }
        public static boolean isHitboxed(){ return includeHitboxes; }
        public static JSONObject getHitboxes(){ return hitboxes; }
        public static String getId(){ return id; }
        public static JSONObject getStructures(){ return Structures; }
        public static Map getTiles(){ return tiles; }

        // specific methods
        public static void changeSize(int newX, int newY){
            sizeX = newX;
            sizeY = newY;
        }

        public static void applyTextureSheet(String texturePath){
            texture = path + texturePath;
            DJaw.DJMessage("Applying TextureSheet patches to TileMap " + id, 0);
        }

        public static void addStructures(JSONObject structures){
            Structures = structures;
            DJaw.DJMessage("Applying StructSheet patches to TileMap " + id, 0);
        }
        @SuppressWarnings("unchecked")
        public static Map getCoords(int x, int y){
            Map coordsIndex = new TreeMap();

            coordsIndex.put("x", Math.min(x, sizeX));
            coordsIndex.put("y", Math.min(y, sizeY));
            return coordsIndex;
        }



        public static JLabel createTileButton(Tile tile) throws NullPointerException {
            DJaw.DJMessage("Loading Button Image...", 0);
            try {
                String paths = Tile.getIMGPath();
                System.out.println(paths);
                URL iconURL = GUI.class.getResource(paths);
                ImageIcon icon = new ImageIcon(iconURL);
                return new JLabel(icon);

            } catch(NullPointerException e){
                DJaw.DJMessage(e.toString(), 1);

                URL iconURLS = GUI.class.getResource("/com/maxus/djaw/images/null.png");
                System.out.println(iconURLS);
                ImageIcon icon = new ImageIcon(iconURLS);
                return new JLabel(icon);
            }

        }

        public static JFrame setupRandomGUIGrid(JFrame mainFrame, String[] tilePaths) throws IOException, NullPointerException {
            DJaw.DJawLogger.INFO("Creating a GUI Map...", DJaw.log);
            JSONObject configData = DJWParser.parseConfig();
            long configFOVX = (long) configData.get("mappingFOVX");
            long configFOVY = (long) configData.get("mappingFOVY");
            long trueFOVX = configFOVX + 1;
            long trueFOVY = configFOVY + 1;

            JPanel innerGUI = new JPanel(new GridBagLayout());
            innerGUI.setBackground(Color.WHITE);
            DJawMapPanel map = new DJawMapPanel();
            map.setBackground(Color.LIGHT_GRAY);
            map.setBorder(BorderFactory.createLineBorder(Color.black));
            for (int y = 0; y < sizeY && y < trueFOVY; y++) {
                JPanel line = new JPanel();
                line.setOpaque(false);
                for (int x = 0; x < sizeX && x < trueFOVX; x++) {
                    try {
                        Tile tmp = new Tile("filler");
                        Random r = new Random();
                        String truepath = tilePaths[r.nextInt(tilePaths.length)];
                        Tile.$reinit("temp","/com/maxus/djaw/images"+truepath);
                        if(!Tile.getIMGPath().isEmpty()) {
                            JLabel temp = createTileButton(tmp);
                            temp.addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent me) {
                                    GUI.popup("Tile clicked!", "You clicked the tile with ID of :" + truepath);
                                }
                            });
                            line.add(temp);
                            temp.validate();
                        } else {
                            throw new NullPointerException("Tile is empty!");
                        }
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }
                map.add(line);
                innerGUI.add(map);
            }
            JButton button = new JButton("Regenerate map!");
            button.addActionListener(a -> {
                JFrame frame = new JFrame();
                try {
                    setupRandomGUIGrid(frame, tilePaths);
                } catch (IOException e) {
                    DJaw.DJawLogger.ERROR("ERROR OCCURRED! " + e.toString(), DJaw.log);
                    throw new com.maxus.djaw.io.IOException(e.fillInStackTrace().getLocalizedMessage(), e);
                }
                String pathToIcon = "/com/maxus/djaw/gui/icon.png";
                URL iconURL = GUI.class.getResource(pathToIcon);
                ImageIcon icon = new ImageIcon(iconURL);
                DJaw.DJMessage("Loading GUI...", 0);

                frame.setIconImage(icon.getImage());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 600);
                frame.setVisible(true);
                mainFrame.dispose();
            });
            JPanel panel = new JPanel();
            panel.add(button);
            JPanel upper = new JPanel();
            JLabel label = new JLabel("You can click on tiles! Also try changing FOV in config.dji!");
            upper.add(label);
            mainFrame.getContentPane().add(BorderLayout.CENTER, innerGUI);
            mainFrame.getContentPane().add(BorderLayout.SOUTH, panel);
            mainFrame.getContentPane().add(BorderLayout.NORTH, upper);
            return mainFrame;
        }
    }

    public static class Tile {
        public static final String path = System.getProperty("user.dir");
        public static String tiletype;
        public static String tilename; // id
        File directory = createDirectory(path+"\\djaw\\tiles\\");
        public static String tileIMGPath = "/com/maxus/djaw/images/" + tilename + ".png"; // path to img

        public Tile(String tileType) throws IOException {
            tiletype = tileType;
            tileIMGPath = path + tilename;
        }

        public static String getName(){ return tilename; }
        public static String getIMGPath(){ return tileIMGPath;}


        /**
         * Reinitialize a tile
         * @param tileName new tile id
         * @param tilesIMGPath new tile path FROM CURRENT FILE!
         **/
        public static void $reinit(String tileName, String tilesIMGPath){
            tilename = tileName;
            tileIMGPath = tilesIMGPath;
        }
    }
    public static class DJawMapPanel extends JPanel {
        @SuppressWarnings("all")
        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            Container c = getParent();
            if (c != null) {
                d = c.getSize();
            } else {
                return new Dimension(10, 10);
            }
            int w = (int) d.getWidth();
            int h = (int) d.getHeight();
            int s = (Math.min(w, h));
            return new Dimension(s, s);
        }
    }
    public static JFrame SetupMapGUI(String[] tilepaths) throws IOException {
        DJaw.DJawLogger.INFO("Creating Map GUI...", DJaw.log);
        String pathToIcon = "/com/maxus/djaw/gui/icon.png";
        System.out.println(pathToIcon);
        URL iconURL = GUI.class.getResource(pathToIcon);
        ImageIcon icon = new ImageIcon(iconURL);
        TileMap.changeSize(10, 10);
        JFrame frame = TileMap.setupRandomGUIGrid(new JFrame("DJaw Inner Map"), tilepaths);
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        return frame;
    }
}
