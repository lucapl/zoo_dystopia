package ZooDystopia;

import java.util.Random;

public class CartesianObject {
    private volatile float x;
    private volatile float y;

    /**
     * @param x to be set
     */
    public synchronized void setX(float x) {
        this.x = x;
    }

    /**
     * @param y to be set
     */
    public synchronized void setY(float y) {
        this.y = y;
    }

    /**
     * @return coordinate x
     */
    public synchronized float getX() {
        return x;
    }

    /**
     * @return coordinate y
     */
    public synchronized float getY() {
        return y;
    }

    public String toString(){
        return "X: "+getX()+" Y: "+getY();
    }

    /**
     * Used to calculate the distance between cartesian objects
     * @param destination
     * @return euclidean distance between this and the destination
     */
    public float lengthTo(CartesianObject destination){
        return (float)Math.sqrt(Math.pow(destination.getX()-getX(),2)+Math.pow(destination.getY()-getY(),2));
    }

    public float angleBetween(CartesianObject cartesianObject){
//        if(getY() == cartesianObject.getY()){
//            return (float)Math.PI/2;
//        }
        float theta = (float)(cartesianObject.getY()-getY())/(float)(cartesianObject.getX()-getX());
        return (float)Math.atan(theta);
    }

    /**
     * Sets the X and Y to equal the X and Y of a given cartesian object
     * @param cartesianObject
     */
    public void set(CartesianObject cartesianObject){
        setX(cartesianObject.getX());
        setY(cartesianObject.getY());
    }

    /**
     * Adds the given cartesian object's X and Y to this object's coordinates
     * @param cartesianObject
     */
    public void add(CartesianObject cartesianObject){
        setX(getX()+cartesianObject.getX());
        setY(getY()+cartesianObject.getY());
    }
    /**
     * Subtracts the given cartesian object's X and Y from this object's coordinates
     * @param cartesianObject
     */
    public void subtract(CartesianObject cartesianObject){
        setX(getX()-cartesianObject.getX());
        setY(getY()-cartesianObject.getY());
    }
    /**
     * Multiplies the given cartesian object's X and Y by this object's coordinates
     * @param cartesianObject
     */
    public void multiply(CartesianObject cartesianObject){
        setX(getX()*cartesianObject.getX());
        setY(getY()*cartesianObject.getY());
    }

    /**
     * Multiplies the coordinates by a constant
     * @param num
     */
    public void multiply(float num){
        setX(getX()*num);
        setY(getY()*num);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof CartesianObject cO)){
            return false;
        }
        return super.equals(o) || getX() == cO.getX() && getY() == cO.getY();
    }
}
