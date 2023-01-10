package ZooDystopia.Utils.Factories;

import java.util.List;
import java.util.Random;

public abstract class EntityFactory extends Factory{

    private List<String> names;

    private List<String> species;

    private float minSpeed;
    private float maxSpeed;
    private float minStrength;
    private float maxStrength;

    public List<String> getNames() {
        return names;
    }

    public List<String> getSpecies() {
        return species;
    }

    public void setSpecies(List<String> species) {
        this.species = species;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public float getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(float minSpeed) {
        this.minSpeed = minSpeed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getMinStrength() {
        return minStrength;
    }

    public void setMinStrength(float minStrength) {
        this.minStrength = minStrength;
    }

    public float getMaxStrength() {
        return maxStrength;
    }

    public void setMaxStrength(float maxStrength) {
        this.maxStrength = maxStrength;
    }

    public float getStrength(){
        return new Random().nextFloat(getMinStrength(),getMaxStrength());
    }
    public float getSpeed(){
        return new Random().nextFloat(getMinSpeed(),getMaxSpeed());
    }
}
