import entities.Node;
import entities.Road;
import search.Heuristic;
import search.SearcherAstar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Heureka {

    private final List<Road> roads;
    private SearcherAstar searcherAstar;

    public Heureka(List<Road> roads) {
        this.roads = roads;
    }


    public String findPath(Node startCrossing, int latEnd, int longEnd) {
        this.searcherAstar = new SearcherAstar(new Heuristic(latEnd, longEnd));
        searcherAstar.addToFrontier(startCrossing);
        return exploreNodes(latEnd, longEnd);
    }

    private String exploreNodes(int latEnd, int longEnd) {
        Node exploringNode = searcherAstar.getAndRemoveLeaf();
        List<Road> availableRoads = getAvailableRoads(exploringNode);
        for (Road road : availableRoads) {
            if (road.getLatEnd() == latEnd && road.getLongEnd() == longEnd) {
                return getPathFromNodeAsString(new Node(exploringNode, road.getLatEnd(), road.getLongEnd()));
            }
            searcherAstar.addToFrontier(new Node(exploringNode, road.getLatEnd(), road.getLongEnd()));
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

