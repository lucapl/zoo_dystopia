//package ZooDystopia.GFX.Sprites;
//
//import ZooDystopia.CartesianObject;
//import ZooDystopia.GFX.Drawing.SpriteDrawing;
//import ZooDystopia.GFX.SpriteSheet;
//
//import java.awt.*;
//
//public class WorldObjectSprite extends BasicSpriteDecorator{
//    private volatile CartesianObject cartesianObject;
//
//    public WorldObjectSprite(BasicSprite wrappedSprite, CartesianObject coords){
//        super(wrappedSprite);
//        this.coords = coords;
//        setPreferredSize(new Dimension(getSpriteSheet().getWidth(),getSpriteSheet().getHeight()));
//        setSize(getSpriteSheet().getWidth(),getSpriteSheet().getHeight());
//        setMaximumSize(getPreferredSize());
//        setVisible(true);
//    }
//
//    public CartesianObject getCartesianObject(){
//        return cartesianObject;
//    }
//
//    public void setCartesianObject(CartesianObject cO){
//        this.cartesianObject = cO;
//    }
//
//    @Override
//    public String getInfo(){
//        return cartesianObject.toString();
//    }
//    @Override
//    public Rectangle getObjectBounds(){
//
//    }
//}
