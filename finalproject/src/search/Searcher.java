package search;

import entities.Node;

import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Searcher {

    private ArrayDeque<Node> frontier;
    private HashSet<Node> frontierSet;

    public Searcher() {
        this.frontier = new ArrayDeque<Node>();
        this.frontierSet = new HashSet<Node>();
    }

    public Node getAndRemoveLeaf() {
        Node n = frontier.pollFirst();
        frontierSet.remove(n);
        return n;
    }

    public void addToFrontier(Node n) {
        frontier.addFirst(n);
        frontierSet.add(n);
    }

    public int countFrontier() {
        return frontier.size();
    }

    public boolean frontierIsEmpty() {
        return frontier.isEmpty();
    }

    public boolean inFrontier(Node n) {
        return frontierSet.contains(n);
    }
}
