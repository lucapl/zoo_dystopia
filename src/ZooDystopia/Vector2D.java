package ZooDystopia;

import java.util.Random;

public class Vector2D extends CartesianObject{

    /**
     * @return the length of the vector
     */
    public float length(){
        return (float)Math.sqrt(Math.pow(getX(),2)+Math.pow(getY(),2));
    }

    /**
     * Used to normalize the vector to the given number
     * @param num new length of the vector
     */
    public void normalizeTo(float num){
        if(length() != 0) {
            multiply(num / length());
        }
    }

    /**
     * Normalizes the vector to length of another
     * @param vector2D
     */
    public void normalizeTo(Vector2D vector2D){
        normalizeTo(vector2D.length());
    }

    /**
     * Rotates the vector using a Givens rotator
     * @param theta angle to rotate by in radians
     */
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

    /**
     * Rotates the vector randomly
     */
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

    /**
     * flips the y component
     */
    public void flipY(){
        setY(-getY());
    }

    /**
     * flips the x component
     */
    public void flipX(){
        setX(-getX());
    }
}
