package ZooDystopia;

import java.util.Random;

public class Vector2D extends CartesianObject{
    public float length(){
        return (float)Math.sqrt(Math.pow(getX(),2)+Math.pow(getY(),2));
    }
    public void normalizeTo(float num){
        if(length() != 0) {
            multiply(num / length());
        }
    }
    public void normalizeTo(Vector2D object){
        normalizeTo(object.length());
    }

    public void rotate(float theta){
        float cosTheta = (float)Math.cos(theta);
        float sinTheta = (float)Math.sin(theta);
        float x,y;
        x = getX();
        y = getY();
        //givens rotator
        setX(x*cosTheta - y*sinTheta);
        setY(x*sinTheta + y*cosTheta);
    }
    public void rotate(){
        Random random = new Random();
        rotate(random.nextFloat(2*(float)Math.PI));
    }
    public void rotateTo(CartesianObject object){
        float theta = angleBetween(object);

        float length = length();
        setX(length);
        setY(0);

        rotate(theta);
    }

    public void flipY(){
        setY(-getY());
    }
    public void flipX(){
        setX(-getX());
    }
}
