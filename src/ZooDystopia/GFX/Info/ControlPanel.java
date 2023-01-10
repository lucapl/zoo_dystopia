package ZooDystopia.GFX.Info;

import ZooDystopia.CartesianObject;
import ZooDystopia.Entities.Entity;
import ZooDystopia.Entities.RunnableEntity;
import ZooDystopia.GFX.BasicButton;
import ZooDystopia.GFX.BasicPanel;
import ZooDystopia.GFX.LimitableVisibility;
import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.Sprites.ImageSprite;
import ZooDystopia.Utils.Controllers.EntityController;
import ZooDystopia.Utils.Factories.PredatorFactory;
import ZooDystopia.Utils.Factories.PreyFactory;
import ZooDystopia.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        setPreyButtonPanel(new PreyButtonPanel());
        getPreyButtonPanel().setVisible(false);
        setCanBeLimited(new ArrayList<>());

        add(infoLabel);
        initiateTheButtons();
        setInfoPanel(new InfoPanel(256,360));
        ImageSprite.setInfoPanel(getInfoPanel());
        getPreyButtonPanel().setInfoPanelInterface(getInfoPanel());
        getPreyButtonPanel().addButtonFunctionality();
        add(getAddPredatorButton());
        add(getAddPreyButton());
        add(getPreyButtonPanel());
        add(getRemoveButton());
        add(getInfoPanel());
        setScrollPane(new JScrollPane(getInfoPanel()));
        getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getScrollPane().setAlignmentX(LEFT_ALIGNMENT);
        add(getScrollPane());
        revalidate();
        repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        recheckVisibilities();
    }
    @Override
    public Component add(Component comp){
        if (comp instanceof LimitableVisibility thing){
            getCanBeLimited().add(thing);
        }
        return super.add(comp);
    }

    private void initiateTheButtons(){
        setAddPredatorButton(new BasicButton("Add predator") {
            @Override
            public boolean shouldBeVisible(VisibilityFlag flag) {
                return true;
            }
        });
        getAddPredatorButton().setVisible(false);
        setAddPreyButton(new BasicButton("Add prey"){
            @Override
            public boolean shouldBeVisible(VisibilityFlag flag) {
                return flag == VisibilityFlag.STRUCTURE;
            }
        });
        getAddPreyButton().setVisible(false);
        setRemoveButton(new BasicButton("Remove"){
            @Override
            public boolean shouldBeVisible(VisibilityFlag flag) {
                return flag == VisibilityFlag.PREY || flag == VisibilityFlag.ENTITY;
            }
        });
        getRemoveButton().setVisible(false);

    }

    /**
     * Add functionality to the buttons
     * @param world in respect to which the buttons will be defined
     */
    public void addButtonFunctionality(World world){
        getAddPredatorButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityController predatorController = new EntityController(new PredatorFactory());
                predatorController.addRandom(world);
            }

        });

        getAddPreyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityController preyController = new EntityController(new PreyFactory());
                preyController.addRandomAt((CartesianObject) getSelectedSprite().getRepresentedObject(),world);
            }

        });

        getRemoveButton().addActionListener(new ActionListener() {
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

    private void recheckVisibilities(){
        VisibilityFlag flag = null;
        if(getInfoPanel() !=null ) {
            flag = getInfoPanel().getVisibilityFlag();
        }
        for(var thing : getCanBeLimited()) {
            setComponentVisibility(thing,flag);
        }
    }
    private void setComponentVisibility(LimitableVisibility thing,VisibilityFlag flag){
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

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(JLabel infoLabel) {
        this.infoLabel = infoLabel;
    }

    public BasicButton getAddPredatorButton() {
        return addPredatorButton;
    }

    public void setAddPredatorButton(BasicButton addPredatorButton) {
        this.addPredatorButton = addPredatorButton;
    }

    public BasicButton getAddPreyButton() {
        return addPreyButton;
    }

    public void setAddPreyButton(BasicButton addPreyButton) {
        this.addPreyButton = addPreyButton;
    }

    public BasicButton getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(BasicButton removeButton) {
        this.removeButton = removeButton;
    }

    public PreyButtonPanel getPreyButtonPanel() {
        return preyButtonPanel;
    }

    public void setPreyButtonPanel(PreyButtonPanel preyButtonPanel) {
        this.preyButtonPanel = preyButtonPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public List<LimitableVisibility> getCanBeLimited() {
        return canBeLimited;
    }

    public void setCanBeLimited(List<LimitableVisibility> canBeLimited) {
        this.canBeLimited = canBeLimited;
    }
}
