package ZooDystopia.Utils;

import java.util.*;
import java.util.stream.Collectors;

public abstract class GraphConnector<Node> {
    private Set<Node> S;
    private Set<Node> T;

    public abstract List<Node> getChildren(Node node);

    public GraphConnector(Collection<Node> nodes){
        S = new HashSet<>(nodes);
        T = new HashSet<>();
    }
    public void visitNode(Node node){
        S.remove(node);
        T.add(node);
    }
    public List<Vector<Node>> randomlyConnect(){
        Randomizer<Node> nodeRandomizer = new Randomizer<>();
        Node current = nodeRandomizer.getRandomFrom(new LinkedList<>(S));
        visitNode(current);
        List<Vector<Node>> edges = new LinkedList<>();

        while (!S.isEmpty()){
            Node child = nodeRandomizer.getRandomFrom(getChildren(current));
            if (!T.contains(child)){
                Vector<Node> edge = new Vector<>();
                edge.add(child);
                edge.add(current);
                edges.add(edge);
                visitNode(child);
            }
            current = child;
        }
        return edges;
    }
}
