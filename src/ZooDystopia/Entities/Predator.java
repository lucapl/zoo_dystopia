package ZooDystopia.Entities;

import ZooDystopia.Utils.Randomizer;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Predator extends RunnableEntity {

    private static float attackRange = 20;
    private PredatorModes mode;//use with PredatorModes

    public static List<Prey> getPrey() {
        return getMap().getPrey();
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
        setMode(defaultMode);
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
        prey.getAttacked(strengthDifference);
        sleep();
        return !prey.isAlive();
    }
    public boolean isInAttackRange(Entity entity){
        return lengthTo(entity) < attackRange;
    }

    @Override
    public void run(){
        setVelocityToNormal();
        //the life loop
        while(isAlive()){
            forcedGo();
            sleep();
            updatePosition();

            switch(getMode()){
                case RELAX -> relaxedSteps();
                case HUNTING -> huntingSteps();
            }
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
        boolean eaten = false;
        if(getDestination() instanceof Corpse corpse && !corpse.isEaten()){
            if(isInAttackRange(corpse)){
                consume(corpse);
                eaten = corpse.isEaten();
            }
        } else if(getDestination() instanceof Prey prey && !prey.isHidden()){
            if(isInAttackRange(prey)){
                kill(prey);
            }

        }else {
            pickDestination();
        }
        if(eaten){
            setDestination(null);
            switchModes();
        }
    }
    public boolean pickDestination(){
        List<Corpse> notEatenCorpses = getCorpses().stream().filter((corpse)->(!corpse.isEaten())).toList();
        if(!notEatenCorpses.isEmpty()){
            //Randomizer<Corpse> corpseRandomizer = new Randomizer<>();
            Corpse closestCorpse = notEatenCorpses.stream().min((corpse1,corpse2)->(Float.compare(corpse1.lengthTo(this),corpse2.lengthTo(this)))).get();
            setDestination(closestCorpse);
            return true;
        }
        //Randomizer<Prey> preyRandomizer = new Randomizer<>();
        List<Prey> notHiddenPrey = getPrey().stream().filter((Prey prey)->(!prey.isHidden())).toList();
        if(!notHiddenPrey.isEmpty()) {
            Prey closestPrey = notHiddenPrey.stream().min((prey1, prey2) -> (Float.compare(prey1.lengthTo(this), prey2.lengthTo(this)))).get();
            setDestination(closestPrey);
            return true;
        }
        return false;
    }

    private List<Corpse> getCorpses() {
        return getMap().getCorpses();
    }
}