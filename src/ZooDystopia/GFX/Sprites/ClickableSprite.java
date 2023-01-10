package ZooDystopia.GFX.Sprites;

import ZooDystopia.GFX.Info.InfoPanelInterface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickableSprite extends BasicSpriteDecorator implements Clickable{
    private InfoPanelInterface infoPanelInterface;
    public ClickableSprite(BasicSprite basicSprite){
        super(basicSprite);
        addClickFunctionality();
    }
    public ClickableSprite(){
        this(null);
    }
    private void addClickFunctionality(){
        ClickableSprite self = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                infoPanelInterface.displayInfo(self);
                infoPanelInterface.setSelectedSprite(self);
            }
        });
    }
}
