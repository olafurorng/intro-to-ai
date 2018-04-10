import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by olafurorn on 4/9/18.
 */
public class SearcherAstar {

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
}
