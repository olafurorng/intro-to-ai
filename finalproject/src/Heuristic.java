import java.util.Comparator;
import java.util.Set;

public abstract class Heuristic implements Comparator<Node> {

    abstract int h(Node n);

    private int f(Node n) {
        return n.g() + this.h(n);
    }

    @Override
    public int compare(Node n1, Node n2) {
        return this.f(n1) - this.f(n2);
    }

    public static class HeuristicRoute extends Heuristic {

        private int endLatitude;
        private int endLongitude;

        public HeuristicRoute(int endLatitude, int endLongitude) {
            this.endLatitude = endLatitude;
            this.endLongitude = endLongitude;
        }

        @Override
        int h(Node n) {
            NodeRoute node = (NodeRoute) n;
            int x1 = node.getLatitude();
            int x2 = this.endLatitude;
            int y1 = node.getLongitude();
            int y2 = this.endLongitude;
            return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        }
    }

    public static class HeuristicLogic extends Heuristic {

        final int numberOfPossibleLiterals;
        final Set<String> literalsToResolveGoal;

        public HeuristicLogic(int numberOfPossibleLiterals, Set<String> literalsToResolveGoal) {
            this.numberOfPossibleLiterals = numberOfPossibleLiterals;
            this.literalsToResolveGoal = literalsToResolveGoal;
        }

        @Override
        int h(Node n) {
            NodeLogic node = (NodeLogic) n;

            int numberOfMissingLiteralToResolveGoal = literalsToResolveGoal.size();
            for (String knownLiteral : node.getKownLiterals()) {
                if (literalsToResolveGoal.contains(knownLiteral)) {
                    numberOfMissingLiteralToResolveGoal--;
                }
            }

            int numberOfUnknownLiterals = numberOfPossibleLiterals - node.getKownLiterals().size();

            return numberOfUnknownLiterals + 10 * numberOfMissingLiteralToResolveGoal;
        }
    }
}