package ZooDystopia.GFX.Sprites;

import java.awt.*;

public class BasicSpriteDecorator extends BasicSprite{
    private BasicSprite wrappedSprite;

    public BasicSpriteDecorator(BasicSprite wrappedSprite){
        super(null);
        this.setWrappedSprite(wrappedSprite);
    }
    @Override
    public void paintComponent(Graphics g){
        getWrappedSprite().paintComponent(g);
    }

    public BasicSprite getWrappedSprite() {
        return wrappedSprite;
    }

    public void setWrappedSprite(BasicSprite wrappedSprite) {
        this.wrappedSprite = wrappedSprite;
    }

    @Override
    public String getInfo() {
        if(getWrappedSprite() != null){
            return getWrappedSprite().getInfo();
        }
        return super.getInfo();
    }
    @Override
    public BasicSprite getLocalSprite(){
        if(getWrappedSprite() != null){
            return getWrappedSprite().getLocalSprite();
        }
        return super.getLocalSprite();
    }
    @Override
    public Rectangle getObjectBounds(){
        if(getWrappedSprite() != null){
            return getWrappedSprite().getObjectBounds();
        }
        return super.getObjectBounds();
    }

    public Object getRepresentedObject(){
        if(getWrappedSprite() != null){
            return getWrappedSprite().getRepresentedObject();
        }
        return super.getRepresentedObject();
    }
}
