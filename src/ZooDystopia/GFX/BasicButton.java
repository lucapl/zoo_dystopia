package ZooDystopia.GFX;

import ZooDystopia.GFX.Info.VisibilityFlag;
import ZooDystopia.GFX.Sprites.Clickable;

import javax.swing.*;
import java.awt.*;

public abstract class BasicButton extends JButton implements LimitableVisibility{
    public boolean shouldBeVisible(VisibilityFlag flag){
        return true;
    }
    public BasicButton(String text){
        super(text);
    }
}
