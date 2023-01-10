package ZooDystopia;

import ZooDystopia.Entities.Corpse;
import ZooDystopia.Entities.Entity;
import ZooDystopia.Entities.RunnableEntity;
import ZooDystopia.Entities.Prey;
import ZooDystopia.GFX.Main.WorldPanel;
import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.Main.Visualizer;
import ZooDystopia.Pathing.Path;
import ZooDystopia.Structures.*;
import ZooDystopia.Utils.Controllers.EntityController;
import ZooDystopia.Utils.Factories.*;
import ZooDystopia.Utils.Filter;
import ZooDystopia.Utils.Mapping.Router;
import ZooDystopia.Utils.Mapping.WorldSections;
import ZooDystopia.Utils.Randomizer;

import java.util.*;

public class World {
    private volatile List<Entity> entities;
    private List<Structure> structures;
    private List<Path> paths;
    private final int WIDTH;
    private final int HEIGHT;
    private WorldSections worldSections;
    private WorldPanel mapPanel;
    private Visualizer visualizer;

    public World(int width, int height, int sectionCount, Visualizer visualizer, WorldPanel jPanel){
        WIDTH = width;
        HEIGHT = height;
        setEntities(Collections.synchronizedList(new ArrayList<>()));
        setStructures(new ArrayList<>());
        setPaths(new ArrayList<>());
        setMapSections(new WorldSections(sectionCount));
        setMapPanel(jPanel);
        this.setVisualizer(visualizer);
    }
    public void random(int waterCount, int plantCount, int hideoutCount,int intersectionCount,int pathCount){
        Random rand = new Random();
        int offset = (int)(0.03*getWIDTH());

        randomStructures(hideoutCount,new HideoutFactory(),offset);

        randomStructures(waterCount,new WaterSourceFactory(),offset);

        randomStructures(plantCount,new PlantFactory(),offset);

        randomStructures(intersectionCount,new IntersectionFactory(),offset);

        int addedPaths = getMapSections().randomlyConnect(this);

        getMapSections().determinePossiblePaths();

        randomPaths(pathCount - addedPaths);

        routeTheWorld();
        mapPanel.revalidate();
        mapPanel.repaint();
    }
    public void add(Entity entity){
        Random rand = new Random();
        entity.setX(rand.nextInt(getWIDTH()));
        entity.setY(rand.nextInt(getHEIGHT()));
        getEntities().add(entity);
    }
    public void addAt(Entity entity,CartesianObject cartesianObject){
        entity.set(cartesianObject);
        getEntities().add(entity);
    }
    public void add(Structure structure){
        getStructures().add(structure);
    }
    public void add(Path path){
        Structure end1,end2;
        end1 = path.getFirstEnd();
        end2 = path.getSecondEnd();
        end1.add(path);
        end2.add(path);
        getPaths().add(path);
        BasicSprite sprite = getVisualizer().visualize(path);
        getMapPanel().add(sprite);
    }
    public void randomStructures(int count, StructureFactory structureFactory, int offset){
        for(int i = 0; i < count; i++){
            Structure structure = (Structure) structureFactory.create();
            int x, y;
            CartesianObject point = getMapSections().getRandomPlace(structure);
            x = (int)point.getX();
            y = (int)point.getY();
            structure.setX(getMapSections().getSectionCoords(x, getWIDTH(),offset));
            structure.setY(getMapSections().getSectionCoords(y, getHEIGHT(),offset));
            add(structure);
            BasicSprite sprite = getVisualizer().visualize(structure);
            getMapPanel().add(sprite);
        }
    }
    public void randomPaths(int count){
//        for(int i = 0; i < count; i++){
//            Randomizer<Structure> randomizer = new Randomizer<>();
//            Structure end1,end2;
//            end1 = randomizer.getRandomFrom(getStructures());
//            end2 = randomizer.getRandomFrom(mapSections.getNeighbours(end1));
//
//            //System.out.println(end1+" "+end2);
//            Path path = new Path(end1,end2);
//            add(path);
//            end1.add(path);
//            end2.add(path);
//            PathSprite sprite = visualizer.makeDrawable(path);
//            mapPanel.add(sprite);
//        }
        int maxPaths = getMapSections().getFreePaths().size();
        Randomizer<Path> pathRandomizer = new Randomizer<>();
        for(int i = 0; i< Math.min(count,maxPaths); i++){
            Path path = getMapSections().getRandomFreePath();
            add(path);
        }
    }
    public synchronized List<Prey> getPrey(){
        Filter<Entity,Prey> preyFilter = new Filter<>(Prey.class);
        return preyFilter.filter(getEntities());
//        List<Prey> prey = new LinkedList<>();
//        for(Entity ent: getEntities()){
//            if(ent instanceof Prey){
//                prey.add((Prey)ent);
//            }
//        }
    }
    public synchronized List<Corpse> getCorpses(){
        Filter<Entity,Corpse> corpseFilter = new Filter<>(Corpse.class);
        return corpseFilter.filter(getEntities());
    }
    public void entityDies(RunnableEntity entity){
        remove(entity);
        EntityController corpseController = new EntityController(new CorpseFactory(entity));
        corpseController.addRandomAt(entity,this);
    }

    public void remove(Entity entity){
        getMapPanel().remove(visualizer.popSprite(entity));
        getEntities().remove(entity);
    }

    public synchronized List<Entity> getEntities() {
        return entities;
    }

    public synchronized void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public WorldSections getMapSections() {
        return worldSections;
    }

    public void setMapSections(WorldSections worldSections) {
        this.worldSections = worldSections;
    }

    public WorldPanel getMapPanel() {
        return mapPanel;
    }

    public void setMapPanel(WorldPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public Visualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(Visualizer visualizer) {
        this.visualizer = visualizer;
    }

    public void routeTheWorld(){
        Router<Plant> plantRouter = new Router<>(Plant.class);
        Router<WaterSource> waterSourceRouter = new Router<>(WaterSource.class);
        Router<Hideout> hideoutRouter = new Router<>(Hideout.class);
        plantRouter.routeThe(this);
        waterSourceRouter.routeThe(this);
        hideoutRouter.routeThe(this);
    }

    public void velocityOutOfBounds(CartesianObject newPos,Vector2D velocity){
        if(newPos.getX() > getWIDTH() || newPos.getX() < 0){
            velocity.flipX();
        }
        if(newPos.getY() > getHEIGHT() || newPos.getY() < 0){
            velocity.flipY();
        }
    }
}
