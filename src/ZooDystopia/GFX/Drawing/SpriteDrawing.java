package ZooDystopia.GFX.Drawing;

import ZooDystopia.GFX.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteDrawing extends Drawing{
    private SpriteSheet spriteSheet;
    public SpriteDrawing(Drawing anotherDrawing,BufferedImage image, Dimension spriteDimensions){
        super(anotherDrawing);
        setSpriteSheet(new SpriteSheet(image,spriteDimensions));
    }
    public SpriteDrawing(BufferedImage image,Dimension spriteDimensions){
        this(null,image,spriteDimensions);
    }
    public SpriteDrawing(SpriteSheet spriteSheet){
        super(null);
        this.spriteSheet = spriteSheet;
    }
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        getSpriteSheet().paint(g);
    }
    @Override
    public Dimension getDrawingDimension(){
        return spriteSheet.getSpriteDimensions();
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }
}
