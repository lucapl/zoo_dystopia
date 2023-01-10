package ZooDystopia.Utils.Factories;

import ZooDystopia.Utils.Randomizer;

import java.util.Random;

public abstract class StructureFactory extends Factory{
    private int minimumCapacity;
    private int maximumCapacity;

    public int getMinimumCapacity() {
        return minimumCapacity;
    }

    public void setMinimumCapacity(int minimumCapacity) {
        this.minimumCapacity = minimumCapacity;
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }
    public int getCapacity(){
        return new Random().nextInt(minimumCapacity,maximumCapacity);
    }
}
