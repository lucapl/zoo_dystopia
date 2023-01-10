package ZooDystopia;

import ZooDystopia.Entities.RunnableEntity;
import ZooDystopia.GFX.Info.ControlPanel;
import ZooDystopia.GFX.Info.InfoFrame;
import ZooDystopia.GFX.Main.WorldPanel;
import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.Sprites.LocalisedImageSprite;
import ZooDystopia.GFX.Sprites.PathSprite;
import ZooDystopia.GFX.Main.MainFrame;
import ZooDystopia.GFX.Main.Visualizer;
import ZooDystopia.Utils.Controllers.EntityController;
import ZooDystopia.Utils.Factories.PredatorFactory;
import ZooDystopia.Utils.Factories.PreyFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZooDystopia {
    private int sectionCount = 7;
    private int waterSourceCount = 10;
    private int plantCount = 10;
    private int hideoutCount = 5;
    private int intersectionCount = 20;
    private int pathCount = 60;
    private Dimension screenSize;
    private MainFrame mainFrame;
    private InfoFrame infoFrame;
    private Visualizer visualizer;
    private World world;
    private int delay = 50;
    public ZooDystopia(){
        setScreenSize(Toolkit.getDefaultToolkit().getScreenSize());
        int SCREENWIDTH = (int) getScreenSize().getWidth();
        int SCREENHEIGHT = (int) getScreenSize().getHeight();
        //System.out.println(SCREENWIDTH + " "+ SCREENHEIGHT);

        int INFOWIDTH = (int)(SCREENWIDTH*0.33);
        int INFOHEIGHT = SCREENHEIGHT - 64;

        int MAINWIDTH = SCREENWIDTH - INFOWIDTH;
        int MAINHEIGHT = SCREENHEIGHT - 64;

        setInfoFrame(new InfoFrame(MAINWIDTH,0,INFOWIDTH,INFOHEIGHT));
        setMainFrame(new MainFrame("Zoo Dystopia",MAINWIDTH,MAINHEIGHT));
        setVisualizer(new Visualizer(0.5F));
        setMap(new World(1000,800, getSectionCount(), getVisualizer(), (WorldPanel) getMainFrame().getMainPanel()));
        RunnableEntity.setMap(getMap());
        BasicSprite.setDEBUGRECT(false);
        LocalisedImageSprite.setMapPanel((WorldPanel) getMainFrame().getMainPanel());
        PathSprite.setMapPanel((WorldPanel) getMainFrame().getMainPanel());
    }

    /**
     * Tells the map to generate
     */
    public void generateTheMap(){
        getMap().random(getWaterSourceCount(), getPlantCount(), getHideoutCount(), getIntersectionCount(), getPathCount());
    }

    /**
     * Function that defines the buttons functionality of the simulation
     */
    public void defineButtonsFunctions(){
        ControlPanel controlPanel = (ControlPanel) getInfoFrame().getMainPanel();
        controlPanel.addPredatorButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityController predatorController = new EntityController(new PredatorFactory());
                predatorController.addRandom(getMap());
            }

        });

        controlPanel.addPreyButtonAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityController preyController = new EntityController(new PreyFactory());
                ControlPanel controlPanel1 = (ControlPanel)(infoFrame.getMainPanel());
                preyController.addRandomAt((CartesianObject) controlPanel1.getSelectedSprite().getRepresentedObject(),getMap());
            }

        });
    }

    /**
     * Function that starts the periodic screen refresher
     */
    public void startTheScreenRefresher(){
        Timer timer = new Timer(getDelay(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getMainFrame().getMainPanel().revalidate();
                getMainFrame().getMainPanel().repaint();
                getInfoFrame().getMainPanel().revalidate();
                getInfoFrame().getMainPanel().repaint();
            }
        });
        timer.start();
    }
    /**
     * Main function
     * @param arguments of application
     */
    public static void main(String[] arguments){
        ZooDystopia zooDystopia = new ZooDystopia();
        zooDystopia.generateTheMap();
        zooDystopia.defineButtonsFunctions();
        zooDystopia.startTheScreenRefresher();
        return;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    public int getWaterSourceCount() {
        return waterSourceCount;
    }

    public void setWaterSourceCount(int waterSourceCount) {
        this.waterSourceCount = waterSourceCount;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public void setPlantCount(int plantCount) {
        this.plantCount = plantCount;
    }

    public int getHideoutCount() {
        return hideoutCount;
    }

    public void setHideoutCount(int hideoutCount) {
        this.hideoutCount = hideoutCount;
    }

    public int getIntersectionCount() {
        return intersectionCount;
    }

    public void setIntersectionCount(int intersectionCount) {
        this.intersectionCount = intersectionCount;
    }

    public int getPathCount() {
        return pathCount;
    }

    public void setPathCount(int pathCount) {
        this.pathCount = pathCount;
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Dimension screenSize) {
        this.screenSize = screenSize;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public InfoFrame getInfoFrame() {
        return infoFrame;
    }

    public void setInfoFrame(InfoFrame infoFrame) {
        this.infoFrame = infoFrame;
    }

    public Visualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(Visualizer visualizer) {
        this.visualizer = visualizer;
    }

    public World getMap() {
        return world;
    }

    public void setMap(World world) {
        this.world = world;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
