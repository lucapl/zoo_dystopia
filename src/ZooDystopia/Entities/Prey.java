package ZooDystopia.Entities;

import ZooDystopia.Consumable;
import ZooDystopia.Pathing.Path;
import ZooDystopia.Pathing.Route;
import ZooDystopia.Structures.*;
import ZooDystopia.Utils.Filter;
import ZooDystopia.Utils.Meter;
import ZooDystopia.Utils.Randomizer;

import java.util.List;
import java.util.Random;

public class Prey extends RunnableEntity {
    private Meter thirst;
    private Meter hunger;

    private Meter sleepiness;
    private boolean hidden = false;
    private boolean walking = true;
    private boolean attacked;

    private boolean consuming;
    private Route goesTo;
    private boolean waiting = false;

    private Structure currentStructure;

    private static float structureEntryRange = 50;

    public Meter getThirst() {
        return thirst;
    }


    public Meter getHunger() {
        return hunger;
    }


    public Prey(String name,String species,float strength,float speed){
        super(name, species, strength, speed);
        thirst = new Meter("Thirst",0,50,30,0.3f);
        hunger = new Meter("Hunger",0,50,20,0.2f);
        setSleepiness(new Meter("Sleepiness", 0, 100, 80, 0.1f));
    }
    /**
     * Proceed to hide at a hideout
     * @param hideout place to hide at
     */
    public void hideAt(Hideout hideout){
        return;
    }

    public boolean isInThreadEntryRange(Structure structure){
        return lengthTo(structure) < structureEntryRange;
    }

    public String toString(){
        return "Prey\n"+super.toString() + "\nGoes to: "+ getGoesTo() + "\nIsAtStructure? " + isAtStructure()+ "\n"+ getThirst()+"\n"+getHunger();// + "\n" + getSleepiness() + "Hidden?: " + isHidden();
    }
    /**
     * Make a baby with a partner
     * @param partner to mix genes with
     * @return the baby
     */
    public Prey reproduceWith(Prey partner){
        Random random = new Random();
        float babyStrength = random.nextFloat(-1,1)+(getStrength() + partner.getStrength())/2;
        float babySpeed = random.nextFloat(-0.25f,0.25f)+(getSpeed() + partner.getSpeed())/2;
        Randomizer randomizer = new Randomizer<>();
        String babyName = randomizer.mixStrings(getName(),partner.getName());
        String babySpecies = randomizer.mixStrings(getSpecies(),partner.getSpecies());
        Prey baby = new Prey(babyName,babySpecies,babyStrength ,babySpeed);

        return baby;
    }
    public void getAttacked(float dif){
        getHealth().update(-Math.max(dif, 0));
        setAttacked(true);
        finishStuff();
        getSleepiness().update(999);
        if(!isAlive()){
            setDeathReason("Killed by a predator");
        }
    }

    @Override
    public void run(){
        Random random = new Random();
        if(random.nextFloat() < 0.0001f){
            getHealth().setValue(-999);
            die();
            setDeathReason("Brain eating amoeba");
        }
        pickClosest();
        while(isAlive() && !isRemoved()){
            sleep();
            updatePosition();

            if(isAtDestination()&&getCurrentStructure() instanceof Routable businessPlace && businessPlace.getRoute() == getGoesTo()){
                doStuffAt(businessPlace);
            }

            walk();

            getHunger().update();
            getThirst().update();
            getSleepiness().update();
            checkNeeds();
        }
        if(!isRemoved()) {
            die();
        }
    }
    public void checkNeeds(){
        if(isThirsty()){
            getHealth().update(-0.05f);
        }
        if(isHungry()){
            getHealth().update(-0.1f);
        }
    }
    public void setDesiredRoute(){
        if(isForcedToGo()){
            return;
        }
        setGoesTo(null);
        if(isThirsty()){
            setGoesTo(Route.ToWaterSource);
        }
        if(isHungry()){
            setGoesTo(Route.ToPlants);
        }
        if(wantsToHide()){
            setGoesTo(Route.ToHideout);
        }
    }
    public void doStuffAt(Routable routable){
        setWalking(false);
        interpolateEntry(currentStructure);
        switch(routable.getRoute()){
            case ToPlants, ToWaterSource -> consume((Consumable) routable);
            case ToHideout -> hide((Hideout)routable);
        }
    }
    public void finishStuff(){
        setWalking(true);
        setConsuming(false);
        setHidden(false);
        setForcedToGo(false);
    }
    public void walk(){
        if(isWalking()) {
            setDesiredRoute();
            updateVelocity();
            if (isAtStructure() && isAtDestination()) {
                interpolateLeave();
            }
            if(isInThreadEntryRange((Structure) getDestination()) && !isAtStructure()){
                enter((Structure) getDestination());
            }
            if(isAtStructure()&&!isInThreadEntryRange(currentStructure)){
                leave();
            }
            if (isAtDestination()) {
                interpolateEntry((Structure) getDestination());
            }
        }
    }

    public void hide(Hideout hideout){
        setHidden(true);
        setAttacked(false);
        Randomizer<Prey> rand = new Randomizer<>();
        Filter<Entity,Prey> preyFilter = new Filter<>(Prey.class);
        Prey partner = rand.getRandomFrom(preyFilter.filter(hideout.getEntitiesAt()));
        reproduceWith(partner);
        getSleepiness().update(-2f);
        if(getSleepiness().isEmpty()){
            finishStuff();
        }
    }

    public void pickDestination(){
        if(getGoesTo() == null){
            stroll();
        }
        Structure destination = (Structure)getDestination();
        List<Path> paths = destination.getPaths();
        for(Path path : paths){
            Structure otherEnd = path.getTheOtherEnd(destination);
            if(path.doesLeadTo(otherEnd,getGoesTo())){
                setDestination(otherEnd);
            }
        }
    }

    @Override
    public void stroll(){
        Structure destination = (Structure)getDestination();
        List<Path> paths = destination.getPaths();
        Randomizer<Path> rand = new Randomizer<>();
        Structure otherEnd = rand.getRandomFrom(paths).getTheOtherEnd(destination);
        setDestination(otherEnd);
    }
    public void pickClosest(){
        List<Structure> structures = getMap().getStructures();
        setDestination(structures.stream()
                .min((struct1,struct2)->(Float.compare(struct1.lengthTo(this),struct2.lengthTo(this))))
                .get());
    }

    public boolean isThirsty() {
        return !getThirst().ifIs();
    }

    public boolean wantsToHide(){
        return !(getSleepiness().ifIs()) || isAttacked();
    }

//    public void setThirsty(boolean thirsty) {
//        this.thirsty = thirsty;
//    }

    public boolean isHungry() {
        return !getHunger().ifIs();
    }

    public synchronized boolean isHidden() {
        return hidden;
    }

    public synchronized void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Route getGoesTo() {
        return goesTo;
    }

    public void setGoesTo(Route goesTo) {
        this.goesTo = goesTo;
    }

    public boolean needsToConsume(){
        return isHungry() || isThirsty();
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public void consume(Consumable consumable){
        setConsuming(true);
        super.consume(consumable);
        if(consumable instanceof WaterSource){
            drink(consumable);
        }
        if(consumable instanceof Plant){
            eat(consumable);
        }
    }
    public void drink(Consumable consumable){
        getThirst().update(-consumable.getFoodValue());
        if(getThirst().isEmpty()){
            finishStuff();
        }
    }
    public void eat(Consumable consumable){
        getHunger().update(-consumable.getFoodValue());
        if(getHunger().isEmpty()) {
            finishStuff();
        }
    }

    public boolean isAtStructure() {
        return currentStructure != null;
    }

    public void interpolateEntry(Structure structure){
        set(structure);
        setVelocity(0,0);
    }
    public void enter(Structure structure){
        try {
            structure.getSemaphore().acquire();
            setCurrentStructure(structure);
            structure.add(this);
        }catch (InterruptedException e){
            e.printStackTrace();
            System.err.println("Thread interrupted");
            structure.getSemaphore().release();
        }
    }
    public void interpolateLeave(){
        setVelocity(0,0);
        pickDestination();
    }
    public void leave(){
        getCurrentStructure().getSemaphore().release();
        getCurrentStructure().remove(this);
        setCurrentStructure(null);
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public Structure getCurrentStructure() {
        return currentStructure;
    }

    public void setCurrentStructure(Structure currentStructure) {
        this.currentStructure = currentStructure;
    }

    public boolean isConsuming() {
        return consuming;
    }

    public void setConsuming(boolean consuming) {
        this.consuming = consuming;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public Meter getSleepiness() {
        return sleepiness;
    }

    public void setSleepiness(Meter sleepiness) {
        this.sleepiness = sleepiness;
    }
}
