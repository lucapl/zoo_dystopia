package ZooDystopia.GFX;

import javax.swing.*;
import java.awt.*;

public class BasicPanel extends JPanel {
    public BasicPanel(int width, int height){
        setSize(width,height);
        setPreferredSize(new Dimension(width, height));
        //setMaximumSize(getPreferredSize());
    }
    public void layer(JComponent c,ZOrders order){
        setComponentZOrder(c,order.ordinal());
    }
}
