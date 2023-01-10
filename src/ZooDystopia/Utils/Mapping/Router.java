package ZooDystopia.Utils.Mapping;

import ZooDystopia.Pathing.Path;
import ZooDystopia.Pathing.Route;
import ZooDystopia.Structures.Routable;
import ZooDystopia.Structures.Structure;
import ZooDystopia.Utils.Filter;
import ZooDystopia.Utils.Searching.AStar;
import ZooDystopia.Utils.Searching.Search;
import ZooDystopia.World;

import java.util.Comparator;
import java.util.List;

public class Router<Target extends Structure & Routable>{
    private Search<Structure> search;
    private Route routesTo;
    private Class<Target> clazz;

    public Router(Class<Target> clazz){
        search = new AStar<Structure>() {
            @Override
            public float h(Structure node1, Structure node2) {
                return node1.lengthTo(node2);
            }
            @Override
            public List<Structure> getChildren(Structure structure) {
                return structure.getChildren();
            }
        };
        this.clazz = clazz;
    }
    public void routeThe(World world){
        Filter<Structure,Target> filter = new Filter<>(clazz);
        List<Target> targets = filter.filter(world.getStructures());
        if(targets.isEmpty()){
            return;
        }
        this.routesTo = targets.get(0).getRoute();

        for(Structure structure: world.getStructures()){
            if(structure.doesLeadTo(routesTo)){
                continue;
            }
            if(structure instanceof Routable route && route.getRoute() == routesTo){
                continue;
            }
            Comparator<Target> structureComparator = new Comparator<>() {
                @Override
                public int compare(Target o1, Target o2) {
                    return (int)(o1.lengthTo(structure) - o2.lengthTo(structure));
                }
            };
            Target goal = targets.stream().min(structureComparator).get();
            List<Structure> path = search.search(structure,goal);
//            if(path.isEmpty()){
//                System.out.println(structure);
//                System.out.println("\n\n");
////                System.out.println(goal);
//                continue;
//            }
            routeAPath(path);
        }
    }
    private void routeAPath(List<Structure> structurePath){
        for(int i = 1; i<structurePath.size();i++){
            Structure end1,end2;
            end1 = structurePath.get(i-1);
            end2 = structurePath.get(i);
            Path path = end1.getPathThatLeadsTo(end2);
            if(!path.doesLeadTo(routesTo)) {
                path.addRouteTo(routesTo, end2);
            }
        }
    }
}
