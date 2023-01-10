package ZooDystopia.GFX.Info;

import ZooDystopia.Entities.Predator;
import ZooDystopia.Entities.Prey;
import ZooDystopia.GFX.BasicButton;
import ZooDystopia.GFX.BasicPanel;
import ZooDystopia.GFX.LimitableVisibility;
import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.Sprites.Clickable;
import org.intellij.lang.annotations.JdkConstants;

import javax.swing.*;
import java.awt.*;

public class PreyButtonPanel extends BasicPanel implements LimitableVisibility {
    private JButton toWater;
    private JButton toPlant;
    private JButton toHideout;
    private JLabel routeLabel;
    public PreyButtonPanel(){
        super(0,0);
        routeLabel = new JLabel("Select the where the prey is supposed to go");
        toWater = new JButton("To water source");
        toHideout = new JButton("To hideout");;
        toPlant = new JButton("To plant");
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel flowPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(flowPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        add(routeLabel);
        add(flowPanel);
        flowPanel.setMaximumSize(flowPanel.getMaximumSize());
        flowPanel.setAlignmentX(LEFT_ALIGNMENT);
        flowPanel.setLayout(new FlowLayout());
        flowPanel.add(toWater);
        flowPanel.add(toHideout);
        flowPanel.add(toPlant);
    }
    public boolean shouldBeVisible(VisibilityFlag visibilityFlag){
        return visibilityFlag == VisibilityFlag.PREY;
    }
}
