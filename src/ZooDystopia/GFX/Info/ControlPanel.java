package ZooDystopia.GFX.Info;

import ZooDystopia.CartesianObject;
import ZooDystopia.Entities.Entity;
import ZooDystopia.Entities.RunnableEntity;
import ZooDystopia.GFX.BasicButton;
import ZooDystopia.GFX.BasicPanel;
import ZooDystopia.GFX.LimitableVisibility;
import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.Sprites.Clickable;
import ZooDystopia.GFX.Sprites.ImageSprite;
import ZooDystopia.Structures.Structure;
import ZooDystopia.Utils.Controllers.EntityController;
import ZooDystopia.Utils.Factories.PredatorFactory;
import ZooDystopia.Utils.Factories.PreyFactory;
import ZooDystopia.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ControlPanel extends BasicPanel {
    private JLabel infoLabel;
    private BasicButton addPredatorButton;
    private BasicButton addPreyButton;
    private BasicButton removeButton;

    private PreyButtonPanel preyButtonPanel;
    private JScrollPane scrollPane;

    private InfoPanel infoPanel;
    private List<LimitableVisibility> canBeLimited;
    public ControlPanel(int width, int height){
        super(width,height);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel infoLabel = new JLabel("Select an object to view its information");
        preyButtonPanel = new PreyButtonPanel();
        preyButtonPanel.setVisible(false);
        canBeLimited = new ArrayList<>();

        add(infoLabel);
        defineButtons();
        setInfoPanel(new InfoPanel(256,360));
        ImageSprite.setInfoPanel(getInfoPanel());
        preyButtonPanel.setInfoPanelInterface(getInfoPanel());
        preyButtonPanel.addButtonFunctionality();
        add(addPredatorButton);
        add(addPreyButton);
        add(preyButtonPanel);
        add(removeButton);
        add(getInfoPanel());
        scrollPane = new JScrollPane(getInfoPanel());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        add(scrollPane);
        revalidate();
        repaint();
    }
    public void addPreyButtonAction(ActionListener action){
        addPreyButton.addActionListener(action);
    }
    public void addPredatorButtonAction(ActionListener action){
        addPredatorButton.addActionListener(action);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        recheckVisibilities();
    }
    @Override
    public Component add(Component comp){
        if (comp instanceof LimitableVisibility thing){
            canBeLimited.add(thing);
        }
        return super.add(comp);
    }
    public void defineButtons(){
        addPredatorButton = new BasicButton("Add predator") {
            @Override
            public boolean shouldBeVisible(VisibilityFlag flag) {
                return true;
            }
        };
        addPredatorButton.setVisible(false);
        addPreyButton = new BasicButton("Add prey"){
            @Override
            public boolean shouldBeVisible(VisibilityFlag flag) {
                return flag == VisibilityFlag.STRUCTURE;
            }
        };
        addPreyButton.setVisible(false);
        removeButton = new BasicButton("Remove"){
            @Override
            public boolean shouldBeVisible(VisibilityFlag flag) {
                return flag == VisibilityFlag.PREY || flag == VisibilityFlag.ENTITY;
            }
        };
        removeButton.setVisible(false);

    }
    public void addButtonFunctionality(World world){
        addPredatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityController predatorController = new EntityController(new PredatorFactory());
                predatorController.addRandom(world);
            }

        });

        addPreyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityController preyController = new EntityController(new PreyFactory());
                preyController.addRandomAt((CartesianObject) getSelectedSprite().getRepresentedObject(),world);
            }

        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Entity removedEntity = (Entity) getSelectedSprite().getRepresentedObject();
                if(removedEntity instanceof RunnableEntity rE){
                    rE.setRemoved(true);
                }
                world.remove(removedEntity);
                getInfoPanel().setSelectedSprite(null);
            }
        });
    }

    public void recheckVisibilities(){
        VisibilityFlag flag = null;
        if(getInfoPanel() !=null ) {
            flag = getInfoPanel().getVisibilityFlag();
        }
        for(var thing : canBeLimited) {
            setComponentVisibility(thing,flag);
        }
    }
    public void setComponentVisibility(LimitableVisibility thing,VisibilityFlag flag){
        thing.setVisible(thing.shouldBeVisible(flag));
    }
    public BasicSprite getSelectedSprite(){
        return getInfoPanel().getSelectedSprite();
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(InfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }
}
