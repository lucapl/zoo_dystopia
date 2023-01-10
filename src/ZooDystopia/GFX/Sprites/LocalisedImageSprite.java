package ZooDystopia.GFX.Sprites;

import ZooDystopia.CartesianObject;
import ZooDystopia.GFX.Drawing.Drawing;
import ZooDystopia.GFX.Info.InfoPanelInterface;
import ZooDystopia.GFX.Main.WorldPanel;
import ZooDystopia.GFX.SpriteSheet;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LocalisedImageSprite extends ImageSprite {
    private static WorldPanel worldPanel;
    public LocalisedImageSprite(Drawing drawing, CartesianObject coords){
        super(drawing,coords);

        LocalisedImageSprite thisSprite = this;
        InfoPanelInterface infoPanel = getInfoPanel();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                infoPanel.displayInfo(thisSprite);
                infoPanel.setSelectedSprite(thisSprite);
            }
        });
    }
    public static WorldPanel getMapPanel() {
        return worldPanel;
    }

    public static void setMapPanel(WorldPanel worldPanel) {
        LocalisedImageSprite.worldPanel = worldPanel;
    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Dimension dimension = getDrawing().getDrawingDimension();
        int x = getMapPanel().getRelativeX(getCoords().getX());
        int y = getMapPanel().getRelativeY(getCoords().getY());
        int imageOffsetX = (int)dimension.getWidth()/2;//(int)(getMapPanel().getRelativeX(getSpriteSheet().getWidth())/2);
        int imageOffsetY = (int)dimension.getHeight()/2;//(int)(getMapPanel().getRelativeY(getSpriteSheet().getHeight())/2);
        setLocation(x-imageOffsetX,y-imageOffsetY);
//        setLocation(x,y);
//        float x = getCoords().getX()/ getMap().getWIDTH();
//        float y = getCoords().getY()/ getMap().getHEIGHT();
//        setAlignmentX(x);
//        setAlignmentY(y);
    }
}
