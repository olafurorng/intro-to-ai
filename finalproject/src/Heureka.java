import java.util.ArrayList;
import java.util.List;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Heureka {

    private List<Road> roads;
    private SearcherAstar searcherAstar;

    public Heureka() {

    }


    public String findRoutePath(List<Road> roads, Node startCrossing, int latEnd, int longEnd) {
        this.roads = roads;
        this.searcherAstar = new SearcherAstar(new Heuristic.HeuristicRoute(latEnd, longEnd));
        searcherAstar.addToFrontier(startCrossing);
        return exploreNodesRoute(latEnd, longEnd);
    }

    private String exploreNodesRoute(int latEnd, int longEnd) {
        NodeRoute exploringNode = (NodeRoute) searcherAstar.getAndRemoveLeaf();
        List<Road> availableRoads = getAvailableRoads(exploringNode);
        for (Road road : availableRoads) {
            if (road.getLatEnd() == latEnd && road.getLongEnd() == longEnd) {
                return getPathFromNodeAsString(new NodeRoute(exploringNode, road.getLatEnd(), road.getLongEnd()));
            }
            searcherAstar.addToFrontier(new NodeRoute(exploringNode, road.getLatEnd(), road.getLongEnd()));
        }

        return exploreNodesRoute(latEnd, longEnd);
    }


    private List<Road> getAvailableRoads(NodeRoute crossing) {
        List<Road> availableRoads = new ArrayList<>();
        for (Road road : roads) {
            if (road.getLatStart() == crossing.getLatitude() && road.getLongStart() == crossing.getLongitude()) {
                availableRoads.add(road);
            }
        }

        return availableRoads;
    }

    private String getPathFromNodeAsString(NodeRoute node) {
        if (node.getParent() == null) {
            return "(" + node.getLatitude() + "," + node.getLongitude() + ")";
        }
        return getPathFromNodeAsString(node.getParent()) + "-> (" + node.getLatitude() + "," + node.getLongitude() + ")";
    }
}

