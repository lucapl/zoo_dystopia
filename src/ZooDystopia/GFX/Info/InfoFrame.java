package ZooDystopia.GFX.Info;

import ZooDystopia.GFX.BasicFrame;

public class InfoFrame extends BasicFrame {

    public InfoFrame(int x, int y, int width, int height){
        super("Info",x,y ,width,height,new ControlPanel(width,height));
    }

}
