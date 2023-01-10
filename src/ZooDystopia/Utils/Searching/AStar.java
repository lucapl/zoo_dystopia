package ZooDystopia.Utils.Searching;

import ZooDystopia.Structures.Structure;

import java.util.*;

public abstract class AStar<Node> implements Search<Node>{
    private Map<Node,Float> costs;

    public AStar(){
        costs = new HashMap<>();
    }

    /**
     * Function that is the heuristic value in AStar algorithm
     * @param node1 first node
     * @param node2 second node
     * @return heuristic length between them
     */
    public abstract float h(Node node1, Node node2);

    /**
     * Function that returns the neighbours of a node
     * @param node in question
     * @return list of its children
     */
    public abstract List<Node> getChildren(Node node);

    private float g(Node node){
        return costs.get(node);
    }

    /**
     * Returns the best
     * @param node
     * @param goal
     * @return
     */
    public float f(Node node,Node goal){
        return g(node) + h(node,goal);
    }
//    private void updateG(Node node, float value){
//        if(value < g(node)){
//            setG(node,value);
//        }
//    }
    private void setG(Node node , float value){
        costs.put(node,value);
    }

    public List<Node> search(Node root,Node goal){
        costs.clear();
//        AStar<Node> thisAstar = this;
//        Comparator<Node> nodeComparator = new Comparator<>() {
//            @Override
//            public int compare(Node o1, Node o2) {
//                return (int)(thisAstar.f(o1,goal)-thisAstar.f(o2,goal));
//            }
//        };
        PriorityQueue<Node> queue = new PriorityQueue<>((node1,node2)->{
            return Float.compare(f(node1,goal),f(node2,goal));
        });
        Map<Node,Node> parent = new HashMap<>();
        Map<String,String> parentReadable = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Set<String> visitedReadable = new HashSet<>();
        queue.add(root);
        setG(root,0);
        Class<?> goalClass = goal.getClass();

        while(!queue.isEmpty()){
            Node current = queue.poll();
            if(current == goal || goalClass.isInstance(current)){
                return constructPath(parent,current);
            }
//            if(visited.contains(current)){
//                continue;
//            }
//            visited.add(current);
            visitedReadable.add(current.getClass().toString());
            for(Node child: getChildren(current)){
//                if(visited.contains(child)){
//                    continue;
//                }
                float newCost = f(current,child);
                if(!(costs.containsKey(child)) || newCost < g(child)){
                    setG(child, newCost);
                    parent.put(child, current);
                    parentReadable.put(child.getClass().toString(),current.getClass().toString());
                    queue.add(child);
                }
            }
        }
//        System.out.println(root);
//        System.out.println(costs);
//        //System.out.println(parentReadable);
//        System.out.println(visitedReadable);
//        System.out.println(getChildren(root));
//
//        for(Node child: getChildren(root)){
////                if(visited.contains(child)){
////                    continue;
////                }
//            System.out.println(!(costs.containsKey(child)));
//        }
        return new LinkedList<>();
    }
    private List<Node> constructPath(Map<Node,Node> parent, Node current){
        LinkedList<Node> path = new LinkedList<>();
        path.add(current);
        while(parent.containsKey(current)){
            current = parent.get(current);
            path.addFirst(current);
        }
        return path;
    }
}
