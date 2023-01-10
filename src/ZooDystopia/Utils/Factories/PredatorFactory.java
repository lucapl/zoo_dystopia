package ZooDystopia.Utils.Factories;

import ZooDystopia.Entities.Predator;
import ZooDystopia.Entities.Prey;
import ZooDystopia.Utils.Randomizer;

import java.util.LinkedList;
import java.util.List;

public class PredatorFactory extends EntityFactory{
    public PredatorFactory(){
        List<String> names = new LinkedList<>();
        names.add("Darek");
        names.add("Warek");
        names.add("Scar");
        names.add("Myśliwy");
        names.add("Jan");
        names.add("Alfa");
        names.add("Beta");
        names.add("Sigma");
        setNames(names);

        List<String> species = new LinkedList<>();
        species.add("Wilk");
        species.add("Pies");
        species.add("Lis");
        species.add("Tygrys");
        setSpecies(species);
    }
    public Object create(){
        Randomizer<String> rand = new Randomizer<>();
        return new Predator(rand.getRandomFrom(getNames()),rand.getRandomFrom(getSpecies()),15,20);
    }
}
