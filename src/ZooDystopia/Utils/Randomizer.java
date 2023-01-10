package ZooDystopia.Utils;

import java.util.*;

public class Randomizer<T> {
    private Random random;
    public Randomizer(){
        this.random = new Random();
    }

    /**
     * Gives a random thing from a list
     * @param list which the item is meant to be rolled from
     * @return the item
     */
    public T getRandomFrom(List<T> list){
        if (list == null || list.isEmpty()){
            return null;
        }
        int i = random.nextInt(list.size());
        return list.get(i);
    }
    public String mixStrings(String string1, String string2){
        int i = random.nextInt(string1.length());
        int j = random.nextInt(string2.length());

        return string1.substring(0,i) + string2.substring(j);
    }
}
