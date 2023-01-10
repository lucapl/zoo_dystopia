package ZooDystopia.GFX.Info;

import ZooDystopia.Entities.Prey;
import ZooDystopia.GFX.BasicPanel;
import ZooDystopia.GFX.LimitableVisibility;
import ZooDystopia.Pathing.Route;
import ZooDystopia.Utils.Controllers.EntityController;
//import org.intellij.lang.annotations.JdkConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreyButtonPanel extends BasicPanel implements LimitableVisibility {
    private JButton toWater;
    private JButton toPlant;
    private JButton toHideout;
    private JLabel routeLabel;
    private InfoPanelInterface infoPanelInterface;
    public PreyButtonPanel(){
        super(0,0);
        setRouteLabel(new JLabel("Select the where the prey is supposed to go"));
        setToWater(new JButton("To water source"));
        setToHideout(new JButton("To hideout"));;
        setToPlant(new JButton("To plant"));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel flowPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(flowPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        add(getRouteLabel());
        add(flowPanel);
        flowPanel.setMaximumSize(flowPanel.getMaximumSize());
        flowPanel.setAlignmentX(LEFT_ALIGNMENT);
        flowPanel.setLayout(new FlowLayout());
        flowPanel.add(getToWater());
        flowPanel.add(getToHideout());
        flowPanel.add(getToPlant());
    }
    public boolean shouldBeVisible(VisibilityFlag visibilityFlag){
        return visibilityFlag == VisibilityFlag.PREY;
    }
    public void addButtonFunctionality(){
        toPlant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prey prey = (Prey)getInfoPanelInterface().getSelectedSprite().getRepresentedObject();
                EntityController entityController = new EntityController(null);
                entityController.forcePreyToGoTo(prey, Route.ToPlants);
            }
        });
        toWater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prey prey = (Prey)getInfoPanelInterface().getSelectedSprite().getRepresentedObject();
                EntityController entityController = new EntityController(null);
                entityController.forcePreyToGoTo(prey, Route.ToWaterSource);
            }
        });
        toHideout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prey prey = (Prey)getInfoPanelInterface().getSelectedSprite().getRepresentedObject();
                EntityController entityController = new EntityController(null);
                entityController.forcePreyToGoTo(prey, Route.ToHideout);
            }
        });
    }

    public JButton getToWater() {
        return toWater;
    }

    public void setToWater(JButton toWater) {
        this.toWater = toWater;
    }

    public JButton getToPlant() {
        return toPlant;
    }

    public void setToPlant(JButton toPlant) {
        this.toPlant = toPlant;
    }

    public JButton getToHideout() {
        return toHideout;
    }

    public void setToHideout(JButton toHideout) {
        this.toHideout = toHideout;
    }

    public JLabel getRouteLabel() {
        return routeLabel;
    }

    public void setRouteLabel(JLabel routeLabel) {
        this.routeLabel = routeLabel;
    }

    public InfoPanelInterface getInfoPanelInterface() {
        return infoPanelInterface;
    }

    public void setInfoPanelInterface(InfoPanelInterface infoPanelInterface) {
        this.infoPanelInterface = infoPanelInterface;
    }
}
