package ZooDystopia.Utils.Factories;

import ZooDystopia.Structures.Hideout;

public class HideoutFactory extends StructureFactory{
    public Object create(){
        return new Hideout(5);
    }
}
