package ZooDystopia.GFX.Drawing;

import java.awt.*;

/**
 * Used for drawing inside a component
 */
public abstract class Drawing {
    private Drawing wrappedDrawing;

    public Drawing(Drawing wrappedDrawing){
        this.setWrappedDrawing(wrappedDrawing);
    }

    /**
     * Defines how the drawing is drawn
     * @param g
     */
    public void draw(Graphics g){
        if(getWrappedDrawing() !=null){
            getWrappedDrawing().draw(g);
        }
    }
    public abstract Dimension getDrawingDimension();

    public Drawing getWrappedDrawing() {
        return wrappedDrawing;
    }

    public void setWrappedDrawing(Drawing wrappedDrawing) {
        this.wrappedDrawing = wrappedDrawing;
    }
}
