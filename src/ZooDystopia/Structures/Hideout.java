package ZooDystopia.Structures;

import ZooDystopia.Pathing.Route;

public class Hideout extends Structure implements Routable{
    public Hideout(int cap){
        super(cap);
    }
    @Override
    public String toString() {
        return "Hideout\n"+super.toString();
    }

    public Route getRoute(){
        return Route.ToHideout;
    }
}
