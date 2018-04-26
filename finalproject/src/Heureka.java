import java.util.*;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Heureka {

    private SearcherAstar searcherAstar;

    // class variables for Inference Engine for Propositional Logic
    private List<Clause> knowledgeBase;
    private Set<String> allLiteralsWithoutTheGoal;
    private NodeLogic lastExploredNode;
    private String goal;

    // class variables for route finding
    private List<Road> roads;


    public Heureka() {

    }

    /*******************************************************
     ******  Inference Engine for Propositional Logic ******
     *******************************************************/

    public String resolveFromKb(List<Clause> knowledgeBase, String goal, Set<String> knownLiterals) {
        this.knowledgeBase = knowledgeBase;
        this.goal = goal;

        // Lets find all literals
        allLiteralsWithoutTheGoal = new HashSet<>();
        Set<String> literalsToResolveGoal = new HashSet<>();
        for (Clause clause : knowledgeBase) {
 
            if (clause.getCnfHashLeft().get(goal) == null) {
                allLiteralsWithoutTheGoal.addAll(clause.getCnfHashLeft().keySet());
                allLiteralsWithoutTheGoal.addAll(clause.getCnfHashRight().keySet());
            }
            else {
                for (String literal : clause.getCnfHashLeft().keySet()) {
                    if (literal.equals(goal)) { // is goal literal
                        // lets find all the literals on the right side which are needed literals to resolve the goal
                        for (String literal2 : clause.getCnfHashRight().keySet()) {
                            literalsToResolveGoal.add(literal2);
                        }
                    } 
                }
            }
        }

        // create the A* search and start searching
        this.searcherAstar = new SearcherAstar(new Heuristic.HeuristicLogic(allLiteralsWithoutTheGoal.size() + 1, literalsToResolveGoal));
        NodeLogic startingNode = new NodeLogic(null, null, knownLiterals);
        searcherAstar.addToFrontier(startingNode);
        return exploreNodesLogic();
    }

    private String exploreNodesLogic() {
        if (searcherAstar.isFrontierEmpty()) {
            Set<String> lastKnownLiterals = lastExploredNode.getKownLiterals();
            Set<String> unResolvedLiterals = new HashSet<>(allLiteralsWithoutTheGoal);
            System.out.println("unResolvedLiterals: " + unResolvedLiterals);
            unResolvedLiterals.removeAll(lastKnownLiterals);
            unResolvedLiterals.add(goal);
            return "No solution was found. Resolved literals are: " + lastKnownLiterals + ". Unresolved literals are: " + unResolvedLiterals;
        }

        NodeLogic exploringNode = (NodeLogic) searcherAstar.getAndRemoveLeaf();
        lastExploredNode = exploringNode;
        for (Clause clause : knowledgeBase) {
            Set<String> leftLiteralsInClause = clause.getCnfHashLeft().keySet();
            Set<String> rightNegativeLiteralsInClause = clause.getCnfHashRight().keySet();



            // check if the clause can be resolved
            boolean clauseCanBeResolved = !leftLiteralsInClause.isEmpty();
            for (String negativeLiteral : rightNegativeLiteralsInClause) {
                if (!exploringNode.getKownLiterals().contains(negativeLiteral)) {
                    clauseCanBeResolved = false;
                    break;
                }
            }
            if (clauseCanBeResolved) {
                NodeLogic newNode = new NodeLogic(exploringNode, clause, leftLiteralsInClause);

                // we check if we have the empty clause
                if (isEmptyClause(leftLiteralsInClause)) {
                    return getResolvedLogicNodesAsString(newNode);
                }

                searcherAstar.addExploredNode(exploringNode);
                if (!searcherAstar.hasBeenExplored(newNode)) {
                    searcherAstar.addToFrontier(newNode);
                }
            }
        }

        return exploreNodesLogic();
    }

    private boolean isEmptyClause(Set<String> positiveLiteralsInClause) {
        // check if any of the literals in 'positiveLiteralsInClause' is not in: "KB ^ -goal"
        for (String newLiteral : positiveLiteralsInClause) {
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
        return getResolvedLogicNodesAsString(node.getParent()) + "| " + node.getLastResolvedClause();
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
        if (searcherAstar.isFrontierEmpty()) {
            return "No solution was found";
        }

        NodeRoute exploringNode = (NodeRoute) searcherAstar.getAndRemoveLeaf();
        List<Road> availableRoads = getAvailableRoads(exploringNode);
        for (Road road : availableRoads) {
            if (road.getLatEnd() == latEnd && road.getLongEnd() == longEnd) {
                return getWholePath(new NodeRoute(exploringNode, road.getLatEnd(), road.getLongEnd(), road.getName()));
            }

            NodeRoute newNode = new NodeRoute(exploringNode, road.getLatEnd(), road.getLongEnd(), road.getName());

            searcherAstar.addExploredNode(exploringNode);
            if (!searcherAstar.hasBeenExplored(newNode)) {
                searcherAstar.addToFrontier(newNode);
            }
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
            return node.getName() + " (" + node.getLatitude() + "," + node.getLongitude() + ")";
        }
        return getPathFromNodeAsString(node.getParent()) + "->" + node.getName() + " (" + node.getLatitude() + "," + node.getLongitude() + ")";
    }

    private String getWholePath(NodeRoute node) {
        String wholePath = getPathFromNodeAsString(node);
        
        String[] splitString = wholePath.split("->");
        String finalString = "";
        String firstCoordinates = "";
        String lastCoordinates = "";
        String lastStreet = "";
        int counter = 0;

        for (String s: splitString) {     
            String[] split = s.split(" ");   
            String street = split[0];
            String coordinates = split[1];

            if (counter == 0) {
                firstCoordinates = coordinates;
            }
            else if (counter == 1) {
                lastStreet = street;
                lastCoordinates = coordinates;           
            }
            else {
                if (!Objects.equals(lastStreet,street)) {
                    finalString += lastStreet + " (" + firstCoordinates + "->" + lastCoordinates + ") -> ";
                    lastStreet = street;
                    firstCoordinates = lastCoordinates;
                    lastCoordinates = coordinates;
                }
                else {
                    lastStreet = street;
                    lastCoordinates = coordinates;
                }
            }

            if (counter == splitString.length - 1) {
                finalString += lastStreet + " (" + firstCoordinates + "->" + lastCoordinates + ")";
            }

            counter++;
        }

        return finalString;
    }
}

