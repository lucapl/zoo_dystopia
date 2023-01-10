package ZooDystopia.GFX.Sprites;

import ZooDystopia.GFX.Drawing.Drawing;

import javax.swing.*;
import java.awt.*;

public class BasicSprite extends JComponent implements Clickable,Boundable{
    private Drawing drawing;
    private Color debugColor = Color.RED;
    private static boolean DEBUGRECT;

    public static boolean isDEBUGRECT() {
        return DEBUGRECT;
    }

    public static void setDEBUGRECT(boolean DEBUGRECT) {
        BasicSprite.DEBUGRECT = DEBUGRECT;
    }

    public BasicSprite(Drawing drawing){
        this.setDrawing(drawing);
        setMaximumSize(drawing.getDrawingDimension());
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        getDrawing().draw(g);
        if(isDEBUGRECT()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(getDebugColor());
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    public Color getDebugColor() {
        return debugColor;
    }

    public void setDebugColor(Color debugColor) {
        this.debugColor = debugColor;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    @Override
    public String getInfo() {
        return "No info";
    }
    @Override
    public BasicSprite getLocalSprite(){
        return new BasicSprite(getDrawing());
    }
    @Override
    public Rectangle getObjectBounds(){
        return getBounds();
    }

    public Object getRepresentedObject(){ return null;}
}
