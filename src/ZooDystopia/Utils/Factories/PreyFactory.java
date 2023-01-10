package ZooDystopia.Utils.Factories;

import ZooDystopia.Entities.Prey;
import ZooDystopia.Utils.Randomizer;

import java.util.LinkedList;
import java.util.List;

public class PreyFactory extends EntityFactory{

    public PreyFactory(){
        List<String> names = new LinkedList<>();
        names.add("Marek");
        names.add("Jarek");
        names.add("Simba");
        names.add("Bambi");
        names.add("Jasiu");
        setNames(names);

        List<String> species = new LinkedList<>();
        species.add("Owca");
        species.add("Jelonek");
        species.add("Baranek");
        species.add("Koza");
        setSpecies(species);
    }
    public Object create(){
        Randomizer<String> rand = new Randomizer<>();
        return new Prey(rand.getRandomFrom(getNames()),rand.getRandomFrom(getSpecies()),10,10);
    }
}
