package ZooDystopia.GFX.Info;

import ZooDystopia.Entities.Entity;
import ZooDystopia.Entities.Prey;
import ZooDystopia.GFX.BasicPanel;
import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.Sprites.Clickable;
import ZooDystopia.Structures.Structure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends BasicPanel implements InfoPanelInterface {

    private BasicSprite selectedSprite;

    private VisibilityFlag visibilityFlag = null;

    public InfoPanel(int width, int height){
        super(width,height);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
        Timer timer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(getSelectedSprite() !=null) {
                    displayInfo();
                }
            }
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(0,0,getWidth()-1,getHeight()-1);
        //displayInfo();
        //g2d.drawLine(0,0,getWidth(),getHeight());
    }
    @Override
    public void displayInfo(Clickable sprite){
        BasicSprite localSprite = sprite.getLocalSprite();//so the paint method is does not include coordinates
        removeAll();
        add(localSprite);
        localSprite.setAlignmentX(0.5F);
        //System.out.println(justSprite.getMaximumSize());
        String theString = sprite.getInfo();
        JLabel thisInfoLabel = new JLabel("<html>" + theString.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
        add(thisInfoLabel);
        thisInfoLabel.setAlignmentX(0.5F);
        revalidate();
        repaint();
    }

//    @Override
//    public void displayInfo(LocalisedSprite localisedSprite){
//
//        displayInfo(justSprite);
//    }
    public void displayInfo(){
        if(getSelectedSprite() != null) {
            displayInfo(getSelectedSprite());
        }
    }

    public void setSelectedSprite(BasicSprite s){
        this.selectedSprite = s;
        Object o = s.getRepresentedObject();
        if (o instanceof Entity){
            setVisibilityFlag(VisibilityFlag.ENTITY);
        }
        if (o instanceof Structure){
            setVisibilityFlag(VisibilityFlag.STRUCTURE);
        }
        if (o instanceof Prey){
            setVisibilityFlag(VisibilityFlag.PREY);
        }
    }

    public BasicSprite getSelectedSprite() {
        return selectedSprite;
    }

    public VisibilityFlag getVisibilityFlag() {
        return visibilityFlag;
    }

    public void setVisibilityFlag(VisibilityFlag visibilityFlag) {
        this.visibilityFlag = visibilityFlag;
    }
}
