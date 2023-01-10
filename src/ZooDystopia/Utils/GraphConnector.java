package ZooDystopia.Utils;

import java.util.*;

public abstract class GraphConnector<Node> {
    private Set<Node> S;
    private Set<Node> T;

    public abstract List<Node> getChildren(Node node);

    public GraphConnector(Collection<Node> nodes){
        setS(new HashSet<>(nodes));
        setT(new HashSet<>());
    }
    public void visitNode(Node node){
        getS().remove(node);
        getT().add(node);
    }
    public List<Vector<Node>> randomlyConnect(){
        Randomizer<Node> nodeRandomizer = new Randomizer<>();
        Node current = nodeRandomizer.getRandomFrom(new LinkedList<>(getS()));
        visitNode(current);
        List<Vector<Node>> edges = new LinkedList<>();

        while (!getS().isEmpty()){
            Node child = nodeRandomizer.getRandomFrom(getChildren(current));
            if (!getT().contains(child)){
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

    public Set<Node> getS() {
        return S;
    }

    public void setS(Set<Node> s) {
        S = s;
    }

    public Set<Node> getT() {
        return T;
    }

    public void setT(Set<Node> t) {
        T = t;
    }
}
