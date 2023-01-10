package ZooDystopia.Utils.Factories;

import java.util.List;

public abstract class EntityFactory extends Factory{

    private List<String> names;

    private List<String> species;

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
}
