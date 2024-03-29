package ZooDystopia.GFX.Drawing;

import ZooDystopia.Utils.Enumerations.LineSlope;

import java.awt.*;

public class LineDrawing extends Drawing{
//    private Integer x1;
//    private Integer x2;
//    private Integer y1;
//    private Integer y2;
//
//    private Integer width;
//    private Integer height;
    private LineSlope slope;
    private int minLineWidth;
    private Dimension dimension;
    private Color color;
    private Stroke stroke;

    public LineDrawing(Drawing anotherDrawing,int minLineWidth,Color color){
        super(anotherDrawing);
        this.setSlope(LineSlope.POSITIVE);
        this.setColor(color);
        this.setMinLineWidth(minLineWidth);
        this.setStroke(new BasicStroke(minLineWidth));
    }
    public LineDrawing(int minLineWidth,Color color){
        this(null,minLineWidth,color);
    }

//    public void updateFields(int x1,int y1,int x2,int y2,int width,int height){
//        this.x1 = x1;
//        this.x2 = x2;
//        this.y1 = y1;
//        this.y2 = y2;
//        this.width = width;
//        this.height = height;
//    }
    public void setFields(LineSlope slope,Dimension dimension){
        this.setSlope(slope);
        this.setDimension(dimension);
        if(getWrappedDrawing() instanceof LineDrawing lineDrawing){
            lineDrawing.setFields(this);
        }
    }
    public void setFields(LineDrawing lineDrawing){
        setFields(lineDrawing.getSlope(), lineDrawing.getDimension());
    }
    @Override
    public void draw(Graphics g) {
//
//        int width = (Math.max((int)Math.abs(x2-x1),minLineWidth));
//        int height = (Math.max((int)Math.abs(y2-y1),minLineWidth));
//        int x = Math.min(x1,x2);
//        int y = Math.min(y1,y2);
//
//        Graphics2D g2d = (Graphics2D) g;
//
//        if (width == minLineWidth){
//            drawLine(width/2,0,width/2,height,g2d);
//        } else if (height == minLineWidth) {
//            drawLine(0,height/2,width,height/2,g2d);
//        }else{
//            int soonerX, soonerY, laterX,laterY;
//            soonerX = (int)Math.min(x1,x2);
//            soonerY = (int)((x1 < x2) ? y1 : y2);
//            laterX = (int)Math.max(x1,x2);
//            laterY = (int)((x1 < x2) ? y2 : y1);
//
//            double a = (laterY-soonerY)/(double)(laterX-soonerX);
//
//            int begin = height;
//            int ending = 0;
//            if (a>0){
//                begin = 0;
//                ending = height;
//            }
//            drawLine(0,begin,width,ending,g2d);
//        }
        int width,height;
        width = (int)getDrawingDimension().getWidth();
        height = (int)getDrawingDimension().getHeight();
        Graphics2D g2d = (Graphics2D) g;
        switch (getSlope()){
            case POSITIVE -> drawLine(0,height,width,0,g2d);
            case NEGATIVE -> drawLine(0,0,width,height,g2d);
            case VERTICAL -> drawLine(width/2,0,width/2,height,g2d);
            case HORIZONTAL -> drawLine(0,height/2,width,height/2,g2d);
        }
        super.draw(g);
    }

    /**
     * Draws a line with current color and brush stroke
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param g2d
     */
    public void drawLine(int x1, int y1,int x2, int y2,Graphics2D g2d){
        g2d.setColor(getColor());
        g2d.setStroke(getStroke());
        g2d.drawLine(x1,y1,x2,y2);
    }
    public Dimension getDrawingDimension(){
        return getDimension();
    }

    public LineSlope getSlope() {
        return slope;
    }

    public void setSlope(LineSlope slope) {
        this.slope = slope;
    }

    public int getMinLineWidth() {
        return minLineWidth;
    }

    public void setMinLineWidth(int minLineWidth) {
        this.minLineWidth = minLineWidth;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
}
