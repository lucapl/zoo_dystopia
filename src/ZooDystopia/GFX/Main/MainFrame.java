package ZooDystopia.GFX.Main;

import ZooDystopia.GFX.BasicFrame;

public class MainFrame extends BasicFrame {
    //private final MapPanel mapPanel;
    public MainFrame(String title, int width, int height){
        super(title,0,0,width,height,new WorldPanel(width,height));
    }
}
