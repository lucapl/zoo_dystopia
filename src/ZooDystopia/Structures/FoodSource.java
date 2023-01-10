package ZooDystopia.Structures;

import ZooDystopia.Consumable;

public abstract class FoodSource extends Structure implements Consumable,Routable {
    private String name;
    private final float replenishRate;

    public FoodSource(String name,int cap, float replenishRate){
        super(cap);
        this.name = name;
        this.replenishRate = replenishRate;
    }
    public String getName(){
        return name;
    }
    @Override
    public float getFoodValue() {
        return getReplenishRate();
    }

    public float getReplenishRate() {
        return replenishRate;
    }
    @Override
    public String toString(){
        return super.toString() + "\nName: "+getName()+"\nSpeed Regeneration: "+ getReplenishRate();
    }

    public boolean equals(Object o){
        if(!(o instanceof FoodSource fD)){
            return false;
        }
        return super.equals(o) && getName() == fD.getName() && getReplenishRate() == fD.getReplenishRate();
    }
}
