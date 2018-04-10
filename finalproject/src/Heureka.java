import entities.Node;
import entities.Road;
import search.Searcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Heureka {

    final Searcher searcher;
    final List<Road> roads;

    public Heureka(List<Road> roads) {
        this.searcher = new Searcher();
        this.roads = roads;
    }


    public String findPath(Node startCrossing, int latEnd, int longEnd) {
        searcher.addToFrontier(startCrossing);
        return exploreNodes(latEnd, longEnd);
    }

    private String exploreNodes(int latEnd, int longEnd) {
        Node exploringNode = searcher.getAndRemoveLeaf();
        List<Road> availableRoads = getAvailableRoads(exploringNode);
        for (Road road : availableRoads) {
            if (road.getLatEnd() == latEnd && road.getLongEnd() == longEnd) {
                return getPathFromNodeAsString(new Node(exploringNode, road.getLatEnd(), road.getLongEnd()));
            }
            searcher.addToFrontier(new Node(exploringNode, road.getLatEnd(), road.getLongEnd()));
        }

        return exploreNodes(latEnd, longEnd);
    }


    private List<Road> getAvailableRoads(Node crossing) {
        List<Road> availableRoads = new ArrayList<>();
        for (Road road : roads) {
            if (road.getLatStart() == crossing.getLatitude() && road.getLongStart() == crossing.getLongitude()) {
                availableRoads.add(road);
            }
        }

        return availableRoads;
    }

    private String getPathFromNodeAsString(Node node) {
        if (node.getParent() == null) {
            return "(" + node.getLatitude() + "," + node.getLongitude() + ")";
        }
        return getPathFromNodeAsString(node.getParent()) + "-> (" + node.getLatitude() + "," + node.getLongitude() + ")";
    }
}

