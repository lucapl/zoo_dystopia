package ZooDystopia.Structures;

import ZooDystopia.Pathing.Route;

public class WaterSource extends FoodSource{
    public WaterSource(String name,int cap,float replenishRate){
        super(name,cap,replenishRate);
    }
    @Override
    public String toString() {
        return "Water Source\n"+super.toString();
    }

    public Route getRoute(){
        return Route.ToWaterSource;
    }
}
