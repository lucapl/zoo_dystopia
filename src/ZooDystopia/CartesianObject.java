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

    public void set(CartesianObject cartesianObject){
        setX(cartesianObject.getX());
        setY(cartesianObject.getY());
    }
    public void add(CartesianObject cartesianObject){
        setX(getX()+cartesianObject.getX());
        setY(getY()+cartesianObject.getY());
    }
    public void subtract(CartesianObject cartesianObject){
        setX(getX()-cartesianObject.getX());
        setY(getY()-cartesianObject.getY());
    }
    public void multiply(CartesianObject cartesianObject){
        setX(getX()*cartesianObject.getX());
        setY(getY()*cartesianObject.getY());
    }
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
