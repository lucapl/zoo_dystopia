package ZooDystopia.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Filter<Thing,Subthing extends Thing> {
    private final Class<Subthing> clazz;

    public Filter(Class<Subthing> clazz) {
        this.clazz = clazz;
    }
//    public Subthing unsafe(Thing thing){
//        if(thing instanceof Subthing) {
//            return (Subthing) thing;
//        }
//        return null;
//    }

    public List<Subthing> filter(List<Thing> things){
        if(things.isEmpty()){
            return null;
        }
        return things.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }
}