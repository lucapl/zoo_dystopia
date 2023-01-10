package ZooDystopia.Pathing;

import ZooDystopia.CartesianObject;
import ZooDystopia.Structures.Structure;

import java.util.*;

public class Path {
    private Vector<Structure> ends;
    //private Vector<List<Route>> leadsTo;
    private Map<Structure, List<Route>> leadsTo;

    public Path(Structure end1, Structure end2){
        ends = new Vector<Structure>(2);
        ends.add(end1);
        ends.add(end2);
        leadsTo = new HashMap<Structure,List<Route>>();
        leadsTo.put(getFirstEnd(),new LinkedList<>());
        leadsTo.put(getSecondEnd(),new LinkedList<>());
    }

    public Path(Vector<Structure> ends){
        this(ends.get(0),ends.get(1));
    }
    public List<Route> getFirstRoutes(){ return leadsTo.get(getFirstEnd());}
    public List<Route> getSecondRoutes(){ return leadsTo.get(getSecondEnd());}
    public Structure getFirstEnd(){
        return ends.get(0);
    }
    public Structure getSecondEnd(){
        return ends.get(1);
    }
    public Structure getEnd(int index){
        assert index == 0 || index == 1;
        return ends.get(index);
    }

    public Structure getTheOtherEnd(Structure end1){
        assert ends.contains(end1);
        return getEnd(1-ends.indexOf(end1));
    }

    public boolean doesLeadTo(Route route){
        return checkRoutes(route,getFirstRoutes()) || checkRoutes(route, getSecondRoutes());
    }
    public boolean doesLeadTo(CartesianObject end, Route leadingRoute){
        assert getFirstEnd()==end || getSecondEnd()==end;
        if(end == getFirstEnd()){
            return checkRoutes(leadingRoute,getFirstRoutes());
        }
        return checkRoutes(leadingRoute,getSecondRoutes());
    }
    private boolean checkRoutes(Route givenRoute,List<Route> givenRoutes){
        return givenRoutes.contains(givenRoute);
    }
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Path path)){
            return false;
        }
        return super.equals(o)
                ||(getFirstEnd() == path.getFirstEnd() && getSecondEnd() == path.getSecondEnd())
                ||(getFirstEnd() == path.getSecondEnd() && getSecondEnd() == path.getFirstEnd());
    }

    public float length(){
        return getFirstEnd().lengthTo(getSecondEnd());
    }
    public void addRouteTo(Route route,Structure structure){
        leadsTo.get(structure).add(route);
    }

    @Override
    public String toString(){
        return super.toString()+"\nPath\nEnd 1: "+ getFirstEnd().getClass().toString()+ "\n" + getFirstRoutes() + "\nEnd2: "+getSecondEnd().getClass().toString()+"\n"+ getSecondRoutes();
    }
}
