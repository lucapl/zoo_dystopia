package ZooDystopia.GFX;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    private Dimension dimension;
    private Dimension spriteDimensions;
    private BufferedImage sheet;
    private int row;
    private int column;
    public SpriteSheet(BufferedImage image,Dimension spriteDimensions){
        setSpriteDimensions(spriteDimensions);
        setSheet(image);
        setRow(0);
        setColumn(0);
        int width,height;
        width = getSheet().getWidth()/getSpriteWidth();
        height = getSheet().getHeight()/getSpriteHeight();
        setDimension(new Dimension(width,height));
    }

    public void paint(Graphics g){
        updateRow();
        updateColumn();
        g.drawImage(getSheet(), -getColumn() * getSpriteWidth(), -getRow() * getSpriteHeight(),null);
    }
    public void updateRow(){
        return;
    }
    public void updateColumn(){
        return;
        //setColumn((getColumn()+1) % ((int)getDimension().getWidth()));
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public BufferedImage getSheet() {
        return sheet;
    }

    public void setSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getSpriteWidth() {
        return (int)getSpriteDimensions().getWidth();
    }
    public int getWidth(){
        return getSpriteWidth();
    }
    public int getHeight(){
        return getSpriteHeight();
    }


    public int getSpriteHeight() {
        return (int)getSpriteDimensions().getHeight();
    }


    public Dimension getSpriteDimensions() {
        return spriteDimensions;
    }

    public void setSpriteDimensions(Dimension spriteDimensions) {
        this.spriteDimensions = spriteDimensions;
    }
}
