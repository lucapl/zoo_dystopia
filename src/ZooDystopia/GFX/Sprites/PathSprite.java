package ZooDystopia.GFX.Sprites;

import ZooDystopia.CartesianObject;
import ZooDystopia.GFX.Drawing.Drawing;
import ZooDystopia.GFX.Drawing.LineDrawing;
import ZooDystopia.GFX.Main.WorldPanel;
import ZooDystopia.Pathing.Path;
import ZooDystopia.Utils.Enumerations.LineSlope;

import java.awt.*;

public class PathSprite extends BasicSprite {
    private Path path;
    private Stroke stroke;
    private Color color = new Color(0x7F3300);
    private int pathWidth;
    private int outlineWidth;
    private Stroke outlineStroke;
    private Color outlineColor = Color.BLACK;

    private static WorldPanel worldPanel;

    public PathSprite(Drawing drawing, Path path, int pathWidth, int outlineWidth){
        super(drawing);
        setDebugColor(Color.BLACK);
        this.path = path;
        stroke = new BasicStroke(pathWidth);
        this.pathWidth = pathWidth+2*outlineWidth;
        this.outlineWidth = outlineWidth;
        outlineStroke = new BasicStroke(this.pathWidth);
//        int x1,y1,x2,y2;
//        CartesianObject start = path.getFirstEnd();
//        CartesianObject end = path.getSecondEnd();
//        x1 = (int)start.getX();
//        y1 = (int) start.getY();
//        x2 = (int)end.getX();
//        y2 = (int)end.getY();
//        int width, height;
//        width = x2-x1;
//        height = y2-y1;
        setSize(1,1);
        setVisible(true);
    }
    public static WorldPanel getMapPanel() {
        return worldPanel;
    }

    public static void setMapPanel(WorldPanel worldPanel) {
        PathSprite.worldPanel = worldPanel;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int x1,y1,x2,y2;
        CartesianObject start = path.getFirstEnd();
        CartesianObject end = path.getSecondEnd();
        x1 = getMapPanel().getRelativeX(start.getX());
        y1 = getMapPanel().getRelativeY( start.getY());
        x2 = getMapPanel().getRelativeX(end.getX());
        y2 = getMapPanel().getRelativeY(end.getY());

        int length = (int)start.lengthTo(end);//(int)Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
        int soonerX, soonerY, laterX,laterY;
        soonerX = (int)Math.min(x1,x2);
        soonerY = (int)((x1 < x2) ? y1 : y2);
        laterX = (int)Math.max(x1,x2);
        laterY = (int)((x1 < x2) ? y2 : y1);
        int width = (Math.max((int)Math.abs(x2-x1),pathWidth));
        int height = (Math.max((int)Math.abs(y2-y1),pathWidth));

        setBounds(soonerX,Math.min(y1,y2),width,height);

        double a = (laterY-soonerY)/(double)(laterX-soonerX);
        LineDrawing lineDrawing = (LineDrawing)getDrawing();
        LineSlope slope = (a<0)?LineSlope.POSITIVE:LineSlope.NEGATIVE;
        if(width == pathWidth){
            slope = LineSlope.VERTICAL;
        }
        if(height == pathWidth){
            slope = LineSlope.HORIZONTAL;
        }
        lineDrawing.setFields(slope,new Dimension(width,height));
        super.paintComponent(g);
//
//        int x = Math.min(x1,x2);
//        int y = Math.min(y1,y2);
//
//
//        setBounds(x, y, width,height );
//
//        if (width == pathWidth){
//            drawLine(width/2,0,width/2,getHeight(),g2d);
//        } else if (height == pathWidth) {
//            drawLine(0,height/2,getWidth(),height/2,g2d);
//        }else{
//
//            int begin = getHeight();
//            int ending = 0;
//            if (a>0){
//                begin = 0;
//                ending = getHeight();
//            }
//            drawLine(0,begin,width,ending,g2d);
//        }
    }
    public void drawLine(int x1, int y1,int x2, int y2,Graphics2D g2d){
        g2d.setColor(outlineColor);
        g2d.setStroke(outlineStroke);
        g2d.drawLine(x1,y1,x2,y2);
        g2d.setColor(color);
        g2d.setStroke(stroke);
        g2d.drawLine(x1,y1,x2,y2);
    }
}
