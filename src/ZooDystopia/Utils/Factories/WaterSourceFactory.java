package ZooDystopia.Utils.Factories;

import ZooDystopia.Structures.WaterSource;
import ZooDystopia.Utils.Randomizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WaterSourceFactory extends FoodSourceFactory{
    public WaterSourceFactory(){
        super();
        List<String> waterSourceNames = new LinkedList<>();
        waterSourceNames.add("Żywiec zdrój");
        waterSourceNames.add("Kropla bezkitu");
        waterSourceNames.add("Muszynianka");
        waterSourceNames.add("Polaris");
        waterSourceNames.add("Saguraro");
        waterSourceNames.add("Primavera");
        waterSourceNames.add("Harnaś");
        waterSourceNames.add("Jurajska");
        waterSourceNames.add("Woda");
        waterSourceNames.add("Jezioro");
        waterSourceNames.add("Halne mocne");
        setNames(waterSourceNames);
        setMaximumReplenishment(4);
    }

    @Override
    public Object create() {
        Randomizer<String> randomizer = new Randomizer<>();
        return new WaterSource(randomizer.getRandomFrom(getNames()),getCapacity(),getReplenishment());
    }
}
