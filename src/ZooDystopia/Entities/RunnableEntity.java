package ZooDystopia.Entities;

import ZooDystopia.CartesianObject;
import ZooDystopia.Consumable;
import ZooDystopia.World;
import ZooDystopia.Utils.Meter;
import ZooDystopia.Vector2D;

import java.util.Random;

public abstract class RunnableEntity extends Entity implements Runnable{
    private Meter health;
    private boolean alive;
    private float speed;
    private volatile Vector2D velocity;
    private volatile CartesianObject destination;

    private boolean forcedToGo;
    private static float LENGTHTODEST = 10f;
    private static float SPEEDFALLOFF = 20f;

    private static World world;

    /**
     * Entity constructor
     * @param name
     * @param species
     * @param strength defines by how much the entity can defend/attack
     * @param speed defines the length of vector of velocity
     */
    public RunnableEntity(String name, String species, float strength, float speed){
        super(name,species,strength);
        float health = 100;
        setHealth(new Meter("Health",health,health,0,0));
        setAlive(true);
        setSpeed(speed);
        setVelocity(new Vector2D());
        //setDestination(new CartesianObject());

    }

    public static World getMap() {
        return world;
    }

    public static void setMap(World world) {
        RunnableEntity.world = world;
    }

    @Override
    public void run(){
        while (isAlive()) {
            sleep();
            updatePosition();
            stroll();
        }
        die();
        return;//calling stop is not thread safe according to the documentation and instead the run method should invoke return
    }
    public void stroll(){
        Random random = new Random();
        float theta = random.nextFloat((float)Math.PI/3) - (float)Math.PI/6;
        //setVelocityToNormal();
        getVelocity().rotate(theta);
        getVelocity().normalizeTo(getSpeed());
    }
    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("Interrupted exception");
        }
    }
    public void sleep(){
        sleep(100);
    }
    public void setVelocityToNormal(){
        setVelocity(1,0);
        getVelocity().normalizeTo(getSpeed());
    }
    public boolean isAtDestination(){
        return lengthToDestination()<LENGTHTODEST;
    }
    public float lengthToDestination(){
        return lengthTo(getDestination());
    }
    public void setVelocity(float x,float y){
        getVelocity().setX(x);
        getVelocity().setY(y);
    }
    public void updatePosition(){
        CartesianObject newPos = new CartesianObject();
        getMap().velocityOutOfBounds(newPos,getVelocity());
        add(getVelocity());
    }
    public void updateVelocity(){
        if(getDestination()==null){
            //setVelocity(0,0);
            return;
        }
        Vector2D difference = new Vector2D();
        difference.set(getDestination());
        difference.subtract(this);
        if(difference.getX() == 0 && difference.getY() == 0){
            setVelocityToNormal();
            return;
        }
        float speed = getSpeed();
        if(lengthToDestination() < SPEEDFALLOFF){
            speed *= lengthToDestination()/SPEEDFALLOFF;
        }
        difference.normalizeTo(speed);
        getVelocity().set(difference);
    }
//    public void checkHealth(){
//        if(){
//            setAlive(false);
//        }
//    }
    @Override
    public String toString(){
        return super.toString()+"\n"+ getHealth() +"\nisAlive: "+ isAlive() +"\nSpeed: "+ getSpeed() +"\nStrength: "+ getStrength()+"\nVelocity"+getVelocity()+"\nDestination: "+getDestination();
    }

    /**
     * Proceeds to go to the specified coordinates
     * @param point coordinates of destination
     */
    public void goTo(CartesianObject point){
        return;
    }

    public synchronized void setHealth(Meter meter){
        health = meter;
    }

    /**
     * Makes the entity consume something
     * @param consumable thing to eat
     */
    public void consume(Consumable consumable){
        getHealth().update(consumable.getFoodValue());
    }

    /**
     * stuff to be done just before dying
     */
    public void die(){
        world.entityDies(this);
    }

    public synchronized Vector2D getVelocity() {
        return velocity;
    }

    public synchronized void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public synchronized CartesianObject getDestination() {
        return destination;
    }

    public synchronized void setDestination(CartesianObject destination) {
        this.destination = destination;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isAlive() {
        return !getHealth().ifIs();
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public synchronized Meter getHealth() {
        return health;
    }

    public void forcedGo(){
        while (isForcedToGo()){
            sleep();
            updateVelocity();
            if(isAtDestination()){
                set(getDestination());
                setForcedToGo(false);
            }
        }
    }

    public boolean isForcedToGo() {
        return forcedToGo;
    }

    public void setForcedToGo(boolean forcedToGo) {
        this.forcedToGo = forcedToGo;
    }
}
