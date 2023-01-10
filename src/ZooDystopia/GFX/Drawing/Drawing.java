package ZooDystopia.GFX.Drawing;

import java.awt.*;

public abstract class Drawing {
    private Drawing anotherDrawing;

    public Drawing(Drawing anotherDrawing){
        this.setAnotherDrawing(anotherDrawing);
    }
    public void draw(Graphics g){
        if(getAnotherDrawing() !=null){
            getAnotherDrawing().draw(g);
        }
    }
    public abstract Dimension getDrawingDimension();

    public Drawing getAnotherDrawing() {
        return anotherDrawing;
    }

    public void setAnotherDrawing(Drawing anotherDrawing) {
        this.anotherDrawing = anotherDrawing;
    }
}
