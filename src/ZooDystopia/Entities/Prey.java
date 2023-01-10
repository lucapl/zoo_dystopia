package ZooDystopia.Entities;

import ZooDystopia.Consumable;
import ZooDystopia.Pathing.Path;
import ZooDystopia.Pathing.Route;
import ZooDystopia.Structures.*;
import ZooDystopia.Utils.Controllers.EntityController;
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
    private volatile Route goesTo;

    private volatile Structure currentStructure;

    private boolean parent = false;
    private boolean baby = false;

    private final static float structureEntryRange = 50;

    public Meter getThirst() {
        return thirst;
    }


    public Meter getHunger() {
        return hunger;
    }



    public Prey(String name,String species,float strength,float speed){
        super(name, species, strength, speed);
        setThirst(new Meter("Thirst",0,50,30,0.3f));
        setHunger(new Meter("Hunger",0,50,20,0.2f));
        setSleepiness(new Meter("Sleepiness", 0, 100, 80, 0.1f));

    }
    @Override
    public void run(){
        Random random = new Random();
        if(random.nextFloat(0,1) < 0.01f){
            getHealth().setValue(-999);
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
        leave();
        if(!isRemoved()) {
            die();
        }
    }
    private boolean isInThreadEntryRange(Structure structure){
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
        setParent(true);
        partner.setBaby(true);
        getSleepiness().update(50);
        Random random = new Random();
        float babyStrength = random.nextFloat(-1,1)+(getStrength() + partner.getStrength())/2;
        float babySpeed = random.nextFloat(-0.25f,0.25f)+(getSpeed() + partner.getSpeed())/2;
        Randomizer randomizer = new Randomizer<>();
        String babyName = randomizer.mixStrings(getName(),partner.getName());
        String babySpecies = randomizer.mixStrings(getSpecies(),partner.getSpecies());
        Prey baby = new Prey(babyName,babySpecies,babyStrength ,babySpeed);
        baby.setBaby(true);
        baby.getSleepiness().setToMax();

        return baby;
    }

    /**
     * Take damage from some source
     * @param dif the damage, most likely the difference in strengths
     */
    public void getAttacked(float dif){
        getHealth().update(-Math.max(dif, 0));
        setAttacked(true);
        //finishStuff();
        getSleepiness().update(999);
    }

    private void checkNeeds(){
        if(isThirsty()){
            getHealth().update(-0.05f);
        }
        if(isHungry()){
            getHealth().update(-0.1f);
        }
    }

    /**
     * Determine, based on the needs where should the prey go
     */
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

    /**
     * Do stuff at a place of interest
     * @param routable place of interest
     */
    public void doStuffAt(Routable routable){
        setWalking(false);
        interpolateEntry(getCurrentStructure());
        switch(routable.getRoute()){
            case ToPlants, ToWaterSource -> consume((Consumable) routable);
            case ToHideout -> hide((Hideout)routable);
        }
    }

    /**
     * Things to change when finishing action at a place of interest
     */
    public void finishStuff(){
        setWalking(true);
        setConsuming(false);
        setHidden(false);
        setForcedToGo(false);
    }

    /**
     * defines the walk behaviour
     */
    public void walk(){
        if(isWalking()) {
            setDesiredRoute();
            updateVelocity();
            if(isInThreadEntryRange((Structure) getDestination()) && !isAtStructure()){
                enter((Structure) getDestination());
            }
            if (isAtDestination()) {
                interpolateEntry((Structure) getDestination());
            }
            if (isAtStructure() && isAtDestination()) {
                interpolateLeave();
            }
            if(isAtStructure()&&!isInThreadEntryRange(getCurrentStructure())){
                leave();
            }

        }
    }

    /**
     * Proceed to hide at a hideout
     * @param hideout place to hide at
     */
    public void hide(Hideout hideout){
        setHidden(true);
        setAttacked(false);
        if(canHaveAChild()) {
            Randomizer<Prey> rand = new Randomizer<>();
            Filter<Entity, Prey> preyFilter = new Filter<>(Prey.class);
            Prey partner;
            synchronized (hideout.getEntitiesAt()) {
                partner = rand.getRandomFrom(preyFilter.filter(hideout.getEntitiesAt()).stream().filter(Prey::canHaveAChild).toList());
            }
            if (partner != null && partner != this) {
                EntityController entityController = new EntityController(null);
                entityController.addAt(reproduceWith(partner), getCurrentStructure(), getWorld());
            }
        }
        getSleepiness().update(-2f);
        if(getSleepiness().isEmpty()){
            finishStuff();
            setParent(false);
            setBaby(false);
        }
    }

    public void pickDestination(){
        if(getGoesTo() == null){
            stroll();
        }
        Structure structure = getCurrentStructure();
        List<Path> paths = structure.getPaths();
        for(Path path : paths){
            Structure otherEnd = path.getTheOtherEnd(structure);
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
        List<Structure> structures = getWorld().getStructures();
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
        return getCurrentStructure() != null;
    }

    public void interpolateEntry(Structure structure){
        set(structure);
        setVelocity(0,0);
    }
    public void enter(Structure structure){
        leave();
        try {
            structure.getSemaphore().acquire();
            setCurrentStructure(structure);
            structure.add(this);
        }catch (InterruptedException e){
            e.printStackTrace();
            System.err.println("Thread interrupted");
            leave();
            structure.getSemaphore().release();
        }
    }
    public void interpolateLeave(){
        setVelocity(0,0);
        pickDestination();
    }
    public void leave(){
        if(getCurrentStructure()!=null) {
            getCurrentStructure().remove(this);
            getCurrentStructure().getSemaphore().release();
            setCurrentStructure(null);
        }
    }

    @Override
    public void die(){
        leave();
        super.die();
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public synchronized Structure getCurrentStructure() {
        return currentStructure;
    }

    public synchronized void setCurrentStructure(Structure currentStructure) {
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

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean madeABaby) {
        this.parent = madeABaby;
    }

    public void setThirst(Meter thirst) {
        this.thirst = thirst;
    }

    public void setHunger(Meter hunger) {
        this.hunger = hunger;
    }

    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    public boolean canHaveAChild(){
        return !isParent() && !isBaby();
    }
}
