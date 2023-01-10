package ZooDystopia.Entities;

import ZooDystopia.Consumable;
import ZooDystopia.Utils.Meter;

import java.util.List;
import java.util.Random;

public class Predator extends RunnableEntity {

    private static float attackRange = 20;
    private PredatorModes mode;//use with PredatorModes

    private Meter HuntingSatisfaction;

    public static List<Prey> getPrey() {
        return getWorld().getPrey();
    }

//    public static List<Corpse> getCorpses() {
//        return corpses;
//    }
    public PredatorModes getMode() {
        return mode;
    }
//    public String getModeString(){
//        if (getMode() == PredatorModes.RELAX) {
//            return "Relax";
//        }
//        return "Hunting";
//    }

    public void setMode(PredatorModes mode) {
        this.mode = mode;
    }
    static private final PredatorModes defaultMode = PredatorModes.HUNTING;

    public Predator(String name,String species,float strength,float speed){
        super(name, species, strength, speed);
        setHuntingSatisfaction(new Meter("",0,100,50,0));
        setMode(defaultMode);
    }
    public boolean isSatisfied(){
        return getHuntingSatisfaction().isFull();
    }

    /**
     * Switches modes between relaxation and hunting
     */
    public void switchModes(){
        if (getMode() == PredatorModes.RELAX){
            setMode(PredatorModes.HUNTING);
            return;
        }
        setMode(PredatorModes.RELAX);
    }
    public String toString(){
        return "Predator\n"+super.toString() + "\nMode: "+ getMode();
    }

    /**
     * Tries to kill a given entity(prey)
     * @param prey to be killed
     * @return if the entity was killed
     */
    public boolean kill(Prey prey){
        float strengthDifference = getStrength() - prey.getStrength();
        getHealth().update(strengthDifference);
        getHuntingSatisfaction().update(strengthDifference);
        prey.getAttacked(strengthDifference);
        setVelocity(0,0);

        return !prey.isAlive();
    }
    public boolean isInAttackRange(Entity entity){
        return lengthTo(entity) < attackRange;
    }

    @Override
    public void run(){
        setVelocityToNormal();
        //the life loop
        while(isAlive()||!isRemoved()){
            forcedGo();
            sleep();
            updatePosition();

            switch(getMode()){
                case RELAX -> relaxedSteps();
                case HUNTING -> huntingSteps();
            }
        }
        if(!isRemoved()) {
            die();
        }
    }
    public void relaxedSteps(){
        Random random = new Random();
        if(random.nextFloat()<0.005){
            switchModes();
        }
        stroll();
    }
    public void huntingSteps(){
        boolean found = true;
        if (getDestination()==null){
            found = pickDestination();
        }
        if(!found){
            switchModes();
            return;
        }

        updateVelocity();
        if(getDestination() instanceof Corpse corpse && !corpse.isEaten()){
            if(isInAttackRange(corpse)){
                consume(corpse);
            }
        } else if(getDestination() instanceof Prey prey && !prey.isHidden() && prey.isAlive()){
            if(isInAttackRange(prey)){
                kill(prey);
            }

        }else {
            setDestination(null);
        }
        if(isSatisfied()){
            setDestination(null);
            getHuntingSatisfaction().setValue(0);
            switchModes();
        }
    }

    @Override
    public void consume(Consumable consumable){
        getHuntingSatisfaction().update(consumable.getFoodValue());
        super.consume(consumable);
    }
    public boolean pickDestination(){
        synchronized (getWorld().getEntities()) {
            List<Corpse> notEatenCorpses = getCorpses().stream().filter((corpse) -> (!corpse.isEaten())).toList();
            if (!notEatenCorpses.isEmpty()) {
                //Randomizer<Corpse> corpseRandomizer = new Randomizer<>();
                Corpse closestCorpse = notEatenCorpses.stream().min((corpse1, corpse2) -> (Float.compare(corpse1.lengthTo(this), corpse2.lengthTo(this)))).get();
                setDestination(closestCorpse);
                return true;
            }
            //Randomizer<Prey> preyRandomizer = new Randomizer<>();
            List<Prey> notHiddenPrey = getPrey().stream().filter((Prey prey) -> (!prey.isHidden())).toList();
            if (!notHiddenPrey.isEmpty()) {
                Prey closestPrey = notHiddenPrey.stream().min((prey1, prey2) -> (Float.compare(prey1.lengthTo(this), prey2.lengthTo(this)))).get();
                setDestination(closestPrey);
                return true;
            }
        }
        return false;
    }

    private List<Corpse> getCorpses() {
        return getWorld().getCorpses();
    }

    public Meter getHuntingSatisfaction() {
        return HuntingSatisfaction;
    }

    public void setHuntingSatisfaction(Meter huntingSatisfaction) {
        HuntingSatisfaction = huntingSatisfaction;
    }
}