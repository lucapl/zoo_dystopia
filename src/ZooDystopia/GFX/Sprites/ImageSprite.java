package ZooDystopia.GFX.Sprites;

import ZooDystopia.CartesianObject;
import ZooDystopia.GFX.Drawing.Drawing;
import ZooDystopia.GFX.Drawing.SpriteDrawing;
import ZooDystopia.GFX.Info.InfoPanelInterface;
import ZooDystopia.GFX.SpriteSheet;

import java.awt.*;

public class ImageSprite extends BasicSprite {
    private volatile CartesianObject coords;
    private static InfoPanelInterface infoPanel;


//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        //System.out.println(coords.getX() + " " + coords.getY());
//        getSpriteSheet().paint(g2d);
//        //g2d.drawImage(getSpriteSheet(),0,0 , null);
//    }

    public ImageSprite(Drawing drawing, CartesianObject coords){
        super(drawing);
        this.coords = coords;
        //setPreferredSize(new Dimension(getSpriteSheet().getWidth(),getSpriteSheet().getHeight()));
        //setSize(getSpriteSheet().getWidth(),getSpriteSheet().getHeight());
        //setMaximumSize(getPreferredSize());
        setPreferredSize(drawing.getDrawingDimension());
        setSize(drawing.getDrawingDimension());
        setMaximumSize(drawing.getDrawingDimension());
        setVisible(true);
    }

//    public SpriteSheet getSpriteSheet() {
//        return null;
//    }
    public CartesianObject getCoords(){
        return coords;
    }
    public static void setInfoPanel(InfoPanelInterface panel){
        infoPanel = panel;
    }
    public static InfoPanelInterface getInfoPanel(){
        return infoPanel;
    }

    @Override
    public String getInfo(){
        return getCoords().toString();
    }
    @Override
    public BasicSprite getLocalSprite(){
        return new ImageSprite(getDrawing(),getCoords());
    }
    @Override
    public Object getRepresentedObject(){
        return getCoords();
    }
}
