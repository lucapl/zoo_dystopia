package ZooDystopia.Utils.Factories;

import java.util.List;

public abstract class FoodSourceFactory extends StructureFactory{
    private List<String> names;
    public List<String> getNames(){return names;}
    public void setNames(List<String> names){
        this.names = names;
    }
}
