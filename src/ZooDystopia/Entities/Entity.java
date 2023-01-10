package ZooDystopia.Entities;

import ZooDystopia.CartesianObject;

public class Entity extends CartesianObject {
    private float strength;
    private String name;
    private String species;
    private String deathReason;

    public Entity(String name,String species, float strength){
        this.setName(name);
        this.setSpecies(species);
        this.setStrength(strength);
    }
    /**
     * @return strength of the entity
     */
    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDeathReason() {
        return deathReason;
    }

    public void setDeathReason(String deathReason) {
        this.deathReason = deathReason;
    }

    public String toString(){
        return super.toString() + "\nEntity"+"\nName: "+ getName() +"\nSpecies: "+ getSpecies();
    }
}
