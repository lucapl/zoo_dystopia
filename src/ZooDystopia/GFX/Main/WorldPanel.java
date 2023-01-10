package ZooDystopia.GFX.Main;

import ZooDystopia.GFX.BasicPanel;

import java.awt.*;

public class WorldPanel extends BasicPanel implements WorldPanelInterface{
    private Color backGroundColor;
    public WorldPanel(int width, int height){
        super(width,height);
        backGroundColor = new Color(7, 185, 7);
        setLayout(null);
    }

    public int getRelativeX(float x){
        return (int)((x * getSize().getWidth())/ (int)getPreferredSize().getWidth());
    }

    public int getRelativeY(float y){
        return (int)((y * getSize().getHeight())/ (int)getPreferredSize().getHeight());
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        setBackground(backGroundColor);
        //Graphics2D graphic2D = (Graphics2D) g;


        //Visualizer visualizer = new Visualizer();
        //graphic2D.drawLine(0,0,getWidth(),getHeight());
        //graphic2D.drawImage(visualizer.getPlantImage(),201,200,null);
//        for (Drawable toDraw : imagesToDraw) {
//            CartesianObject coordinates = toDraw.getCoords();
//            BufferedImage image = toDraw.getImage();
//
//            graphic.drawImage(image, (int) coordinates.getX(), (int) coordinates.getY(), null);
//        }
        //graphic.drawImage(visualizer.getCorpseImage(),120,120,null);
    }
}
