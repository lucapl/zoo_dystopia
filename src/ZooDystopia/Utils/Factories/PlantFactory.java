package ZooDystopia.Utils.Factories;

import ZooDystopia.Structures.Plant;
import ZooDystopia.Structures.WaterSource;
import ZooDystopia.Utils.Randomizer;

import java.util.LinkedList;
import java.util.List;

public class PlantFactory extends FoodSourceFactory{
    public PlantFactory(){
        List<String> plantNames = new LinkedList<>();
        plantNames.add("Marihuana");
        plantNames.add("Krzak");
        plantNames.add("Zioło");
        plantNames.add("Chwast");
        plantNames.add("Palma");
        plantNames.add("Borówki");
        setNames(plantNames);
        setMaximumReplenishment(3);
    }

    @Override
    public Object create() {
        Randomizer<String> randomizer = new Randomizer<>();
        return new Plant(randomizer.getRandomFrom(getNames()),getCapacity(),getReplenishment());
    }
}
