package ZooDystopia.GFX.Sprites;

import ZooDystopia.CartesianObject;
import ZooDystopia.GFX.Main.WorldPanelInterface;

import java.awt.*;

public class WorldLocalisedSprite extends BasicSpriteDecorator{
    private WorldPanelInterface worldPanelInterface;
    private Rectangle bounds;
    public WorldLocalisedSprite(BasicSprite wrappedSprite){
        super(wrappedSprite);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        getWrappedSprite().setBounds(getWrappedSprite().getObjectBounds());
    }
}
