package ZooDystopia.GFX.Info;

import ZooDystopia.GFX.Sprites.BasicSprite;
import ZooDystopia.GFX.Sprites.Clickable;
import ZooDystopia.GFX.Sprites.ImageSprite;

public interface InfoPanelInterface {
    /**
     * Displays the info about a sprite in the panel
     * @param sprite
     */
    public void displayInfo(Clickable sprite);
    //public void displayInfo(LocalisedSprite localisedSprite);

    public void setSelectedSprite(BasicSprite sprite);

    public BasicSprite getSelectedSprite();
}
