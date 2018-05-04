import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class SearcherAstar {

    private Set<Node> exploredNodes = new HashSet<>();
    private PriorityQueue<Node> frontier;
    private HashSet<Node> frontierSet;
    private Heuristic heuristic;

    public SearcherAstar(Heuristic h) {
        this.heuristic = h;
        frontier = new PriorityQueue<Node>(heuristic);
        frontierSet = new HashSet<Node>();
    }

    public Node getAndRemoveLeaf() {
        Node n = frontier.poll();
        frontierSet.remove(n);
        return n;
    }

    public void addToFrontier(Node n) {
        frontier.add(n);
        frontierSet.add(n);
    }

    public boolean isFrontierEmpty() {
        return frontier.size() == 0;
    }

    public boolean hasBeenExplored(Node node) {
        return exploredNodes.contains(node);
    }

    public void addExploredNode(Node node) {
        exploredNodes.add(node);
    }
}