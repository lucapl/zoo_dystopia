package ZooDystopia.Structures;

import ZooDystopia.Pathing.Route;

public class Plant extends FoodSource{
    public Plant(String name,int cap,float replenishRate){
        super(name,cap,replenishRate);
    }
    @Override
    public String toString() {
        return "Plant\n"+super.toString();
    }

    public Route getRoute(){
        return Route.ToPlants;
    }
}
