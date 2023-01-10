package ZooDystopia.Utils.Factories;

import ZooDystopia.Structures.Hideout;

public class HideoutFactory extends StructureFactory{
    public HideoutFactory(){
        setMaximumCapacity(6);
        setMinimumCapacity(2);
    }
    public Object create(){
        return new Hideout(getCapacity());
    }
}
