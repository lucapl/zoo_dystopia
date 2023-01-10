package ZooDystopia.GFX;

import ZooDystopia.GFX.Info.VisibilityFlag;
import ZooDystopia.GFX.Sprites.Clickable;

public interface LimitableVisibility {
    public boolean shouldBeVisible(VisibilityFlag flag);
    public void setVisible(boolean bool);
}
