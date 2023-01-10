package ZooDystopia.GFX.Main;

import ZooDystopia.CartesianObject;
import ZooDystopia.Entities.*;
import ZooDystopia.GFX.Drawing.Drawing;
import ZooDystopia.GFX.Drawing.LineDrawing;
import ZooDystopia.GFX.Drawing.SpriteDrawing;
import ZooDystopia.GFX.SpriteSheet;
import ZooDystopia.GFX.Sprites.*;
import ZooDystopia.Pathing.Path;
import ZooDystopia.Structures.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Visualizer {
    private final BufferedImage preyImage;
    private final BufferedImage predatorImage;
//    private final BufferedImage relaxedPredatorImage;
//    private final BufferedImage huntingPredatorImage;
    private final BufferedImage corpseImage;
    private final BufferedImage hideoutImage;
    private final BufferedImage plantImage;
    private final BufferedImage waterImage;
    private final BufferedImage intersectionImage;

    private final Dimension entitySpriteDimension;
    private final Dimension structureSpriteDimension;

    private Map<CartesianObject,BasicSprite> assignedSprite;

    private float scale;
    private int pathWidth = 8;
    private int outlineWidth = 2;
    public float getScale(){
        return scale;
    }

    public Visualizer(float aScale){
        setScale(aScale);
        preyImage = loadImage("prey.png");
        predatorImage = loadImage("predator.png");
        //relaxedPredatorImage = loadImage("predatorRelax.png");
        //huntingPredatorImage = loadImage("predatorHunt.png");
        corpseImage = loadImage("corpse.png");
        plantImage = loadImage("plant.png");
        waterImage = loadImage("water.png");
        hideoutImage = loadImage("hideout.png");
        intersectionImage = loadImage("intersection.png");
        entitySpriteDimension = new Dimension(128,64);
        structureSpriteDimension = new Dimension(128,128);
        setAssignedSprite(new HashMap<>());
    }

    /**
     * Function to load a BufferedImage from resources
     * @param name of the file
     * @return the image, scaled
     */
    public BufferedImage loadImage(String name){
        InputStream stream = null;
        try {
            stream = getClass().getResourceAsStream("\\gfx\\"+name);
            BufferedImage temp = ImageIO.read(stream);
            int newWidth = (int)(getScale()*temp.getWidth());
            int newHeight = (int)(getScale()*temp.getHeight());
            Image scaledTemp = temp.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);
            BufferedImage scaled = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_ARGB);
            scaled.getGraphics().drawImage(scaledTemp,0,0,null);
            return scaled;
        }catch (IOException io){
            io.printStackTrace();
            System.out.println("Can't find an image named: "+name);
        }catch (IllegalArgumentException eae){
            eae.printStackTrace();
            System.out.println(stream);
        }
        return null;
    }
    public BasicSprite createSprite(SpriteSheet spriteSheet, CartesianObject thing){
        Drawing drawing = new SpriteDrawing(spriteSheet);
        BasicSprite sprite = new LocalisedImageSprite(drawing,thing);
        assignSprite(thing,sprite);
        return sprite;
    }

    public void assignSprite(CartesianObject thing, BasicSprite sprite){
        getAssignedSprite().put(thing,sprite);
    }

    public BasicSprite popSprite(CartesianObject thing){
        return getAssignedSprite().remove(thing);
    }

    public BasicSprite createSprite(Path path){
        Drawing drawing = new LineDrawing(
                new LineDrawing(getPathWidth(),new Color(0x7F3300)),
                2* getOutlineWidth() + getPathWidth(),Color.BLACK);
        return new PathSprite(drawing,path, getPathWidth(), getOutlineWidth());
    }
    /**
     * Function to make a thing drawable
     * @param thing to be drawn
     */
    public BasicSprite visualize(CartesianObject thing){
        if(thing instanceof RunnableEntity){
            return visualize((RunnableEntity) thing);
        }
        if(thing instanceof  Structure){
            return visualize((Structure) thing);
        }
        return null;
    }
    /**
     * Function to make a predator drawable, to adjust for different modes
     * @param predator to be drawn
     */
    public BasicSprite visualize(Predator predator){
//        if(predator.getMode() == PredatorModes.RELAX){
//            return new LocalisedSprite(getRelaxedPredatorImage(),predator);
//        }
//        return new LocalisedSprite(getHuntingPredatorImage(),predator);
        SpriteSheet predatorSpriteSheet = new SpriteSheet(getPredatorImage(), getEntitySpriteDimension()){
            @Override
            public void updateRow(){
                setRow(predator.getMode().ordinal());
            }
        };
        return createSprite(predatorSpriteSheet,predator);
    }
    public BasicSprite visualize(Prey prey){
        SpriteSheet preySpriteSheet = new SpriteSheet(getPreyImage(),getEntitySpriteDimension()){
            @Override
            public void updateRow(){
                setRow(0);
                if(prey.isConsuming()){
                    setRow(1);
                }
                if(prey.isHidden()){
                    setRow(2);
                }
                if(prey.isAttacked()) {
                    setRow(3);
                }
            }
        };
        return createSprite(preySpriteSheet,prey);
    }
    public BasicSprite visualize(Corpse corpse){
        SpriteSheet corpseSpriteSheet = new SpriteSheet(getCorpseImage(),getEntitySpriteDimension()){
            @Override
            public void updateRow(){ setRow(corpse.isEaten()?1:0);}
        };
        return createSprite(corpseSpriteSheet,corpse);
    }
    public BasicSprite visualize(Entity entity){
        if(entity instanceof RunnableEntity){
            return visualize((RunnableEntity) entity);
        }
        if(entity instanceof Corpse){
            return visualize((Corpse) entity);
        }
        return null;
    }
    public BasicSprite visualize(RunnableEntity entity){
        if(entity instanceof Prey){
            return visualize((Prey) entity);
        }
        if(entity instanceof Predator){
            return visualize((Predator) entity);
        }
        return null;
    }
    public BasicSprite visualize(WaterSource waterSource){
        return createSprite(new SpriteSheet(getWaterImage(), getStructureSpriteDimension()),waterSource);
    }
    public BasicSprite visualize(Hideout hideout){
        return createSprite(new SpriteSheet(getHideoutImage(), getStructureSpriteDimension()),hideout);
    }
    public BasicSprite visualize(Plant plant){
        return createSprite(new SpriteSheet(getPlantImage(), getStructureSpriteDimension()),plant);
    }

    public BasicSprite visualize(Intersection intersection){
        return createSprite(new SpriteSheet(getIntersectionImage(), getStructureSpriteDimension()),intersection);
    }

    public BasicSprite visualize(Structure structure){
        if(structure instanceof Plant){
            return visualize((Plant) structure);
        }
        if(structure instanceof WaterSource){
            return visualize((WaterSource) structure);
        }
        if(structure instanceof Hideout){
            return visualize((Hideout) structure);
        }
        if(structure instanceof Intersection){
            return visualize((Intersection) structure);
        }
        return null;
    }

    public BasicSprite visualize(Path path){
        return createSprite(path);
    }

    public BufferedImage getPreyImage() {
        return preyImage;
    }
//
//    public BufferedImage getRelaxedPredatorImage() {
//        return relaxedPredatorImage;
//    }
//
//    public BufferedImage getHuntingPredatorImage() {
//        return huntingPredatorImage;
//    }

    public BufferedImage getCorpseImage() {
        return corpseImage;
    }

    public BufferedImage getHideoutImage() {
        return hideoutImage;
    }

    public BufferedImage getPlantImage() {
        return plantImage;
    }

    public BufferedImage getWaterImage() {
        return waterImage;
    }
    public BufferedImage getIntersectionImage() {
        return intersectionImage;
    }

    public BufferedImage getPredatorImage() {
        return predatorImage;
    }

    public Dimension getEntitySpriteDimension() {
        return scaleDimension(entitySpriteDimension);
    }

    public Dimension scaleDimension(Dimension dimension){
        return new Dimension((int)(dimension.getWidth()*getScale()),(int)(dimension.getHeight()*getScale()));
    }

    public Dimension getStructureSpriteDimension() {
        return scaleDimension(structureSpriteDimension);
    }

    public Map<CartesianObject, BasicSprite> getAssignedSprite() {
        return assignedSprite;
    }

    public void setAssignedSprite(Map<CartesianObject, BasicSprite> assignedSprite) {
        this.assignedSprite = assignedSprite;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getPathWidth() {
        return pathWidth;
    }

    public void setPathWidth(int pathWidth) {
        this.pathWidth = pathWidth;
    }

    public int getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(int outlineWidth) {
        this.outlineWidth = outlineWidth;
    }
}