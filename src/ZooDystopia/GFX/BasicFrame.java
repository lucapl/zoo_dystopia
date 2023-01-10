package ZooDystopia.GFX;

import javax.swing.*;
import java.awt.*;

public abstract class BasicFrame extends JFrame {
    private final BasicPanel mainPanel;
    public BasicFrame(String title,int x, int y, int width, int height,BasicPanel panel){
        super(title);
        //setContentPane(new MainPanel(width,height));
        //setSize(width,height);
        mainPanel = panel;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        setLocationRelativeTo(null);
        setLocation(x,y);
        pack();
        setVisible(true);
    }
    public void addToPanel(JComponent c){
        mainPanel.add(c);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public void layerInPanel(JComponent c, ZOrders order){
        mainPanel.layer(c,order);
    }
    public JPanel getMainPanel(){
        return mainPanel;
    }
}
