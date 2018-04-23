import java.util.*;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Heureka {

    private SearcherAstar searcherAstar;

    // class variables for Inference Engine for Propositional Logic
    private List<Clause> knowledgeBase;
    private Set<Character> allLiteralsWithoutTheGoal;
    private Character goal;

    // class variables for route finding
    private List<Road> roads;


    public Heureka() {

    }

    /*******************************************************
     ******  Inference Engine for Propositional Logic ******
     *******************************************************/

    public String resolveFromKb(List<Clause> knowledgeBase, Character goal, List<Character> knownLiterals) {
        this.knowledgeBase = knowledgeBase;
        this.goal = goal;

        // find all literals
        allLiteralsWithoutTheGoal = new HashSet<>();
        for (Clause clause : knowledgeBase) {
            HashMap<Character, Boolean> literals = clause.getCnfHash();
            for (Character literal : literals.keySet()) {
                allLiteralsWithoutTheGoal.add(literal);
            }
        }
        allLiteralsWithoutTheGoal.remove(goal);


        // create the A* search and start searching
        this.searcherAstar = new SearcherAstar(new Heuristic.HeuristicLogic());
        NodeLogic startingNode = new NodeLogic(null, null, knownLiterals);
        searcherAstar.addToFrontier(startingNode);
        return exploreNodesLogic();
    }

    private String exploreNodesLogic() {
        NodeLogic exploringNode = (NodeLogic) searcherAstar.getAndRemoveLeaf();
        for (Clause clause : knowledgeBase) {
            List<Character> positiveLiteralsInClause = new ArrayList<>();
            List<Character> negativeLiteralsInClause = new ArrayList<>();

            HashMap<Character, Boolean> literals = clause.getCnfHash();
            for (Character literal : literals.keySet()) {
                if (literals.get(literal)) {
                    positiveLiteralsInClause.add(literal);
                } else {
                    negativeLiteralsInClause.add(literal);
                }
            }



            // check if the clause can be resolved
            boolean clauseCanBeResolved = !negativeLiteralsInClause.isEmpty();
            for (Character negativeLiteral : negativeLiteralsInClause) {
                if (!exploringNode.getKownLiterals().contains(negativeLiteral)) {
                    clauseCanBeResolved = false;
                    break;
                }
            }
            if (clauseCanBeResolved) {
                NodeLogic newNode = new NodeLogic(exploringNode, clause, positiveLiteralsInClause);

                // we check if we have the empty clause
                if (isEmptyClause(positiveLiteralsInClause)) {
                    return getResolvedLogicNodesAsString(newNode);
                }

                searcherAstar.addToFrontier(newNode);
            }
        }

        return exploreNodesLogic();
    }

    private boolean isEmptyClause(List<Character> positiveLiteralsInClause) {
        // check if any of the literals in 'positiveLiteralsInClause' is not in: "KB ^ -goal"
        for (Character newLiteral : positiveLiteralsInClause) {
            if (!allLiteralsWithoutTheGoal.contains(newLiteral)) {
                return true;
            }
        }

        return false;
    }

    private String getResolvedLogicNodesAsString(NodeLogic node) {
        if (node.getParent() == null) {
            return "" + node.getKownLiterals();
        }
        return getResolvedLogicNodesAsString(node.getParent()) + "-> " + node.getLastResolvedClause();
    }


    /*******************************************************
     ******************* Route finding *********************
     *******************************************************/


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

