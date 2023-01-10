package ZooDystopia.Entities;

import ZooDystopia.Consumable;
import ZooDystopia.Utils.Meter;

/**
 * Replaces dead entities
 */
public class Corpse extends Entity implements Consumable {

    private Meter foodValueMeter;
    private static int foodMultiplier = 10;

    public Corpse(String name,String species,float strength){
        super(name,species,strength);
        float max = strength*foodMultiplier;
        setFoodValueMeter(new Meter("Food value",max,max,0,-foodMultiplier));
    }
    /**
     * @return how much protein the corpse is worth
     */
    public synchronized float getFoodValue(){
        if(!isEaten()) {
            foodValueMeter.update();
            return foodMultiplier;
        }
        return 0f;
    }

    public synchronized boolean isEaten() {
        return foodValueMeter.isEmpty();
    }


    @Override
    public String toString(){
        return super.toString() + "\n"+getFoodValueMeter() + "\nDeath reason: " +getDeathReason();
    }

    public Meter getFoodValueMeter() {
        return foodValueMeter;
    }

    public void setFoodValueMeter(Meter foodValueMeter) {
        this.foodValueMeter = foodValueMeter;
    }
}
