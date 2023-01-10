package ZooDystopia.Utils.Factories;

import java.util.List;
import java.util.Random;

public abstract class FoodSourceFactory extends StructureFactory{
    private List<String> names;
    private float minimumReplenishment;
    private float maximumReplenishment;
    public List<String> getNames(){return names;}
    public void setNames(List<String> names){
        this.names = names;
    }
    public FoodSourceFactory(){
        setMaximumCapacity(4);
        setMinimumCapacity(1);
        setMinimumReplenishment(1);
        setMaximumReplenishment(2);
    }
    public float getReplenishment(){
        return new Random().nextFloat(getMinimumReplenishment(),getMaximumReplenishment());
    }
    public float getMinimumReplenishment() {
        return minimumReplenishment;
    }

    public void setMinimumReplenishment(float minimumReplenishment) {
        this.minimumReplenishment = minimumReplenishment;
    }

    public float getMaximumReplenishment() {
        return maximumReplenishment;
    }

    public void setMaximumReplenishment(float maximumReplenishment) {
        this.maximumReplenishment = maximumReplenishment;
    }
}
