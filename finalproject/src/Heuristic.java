

import java.util.Comparator;

/**
 * Created by olafurorn on 4/10/18.
 */
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
            return Math.abs(node.getLatitude() - this.endLatitude) + Math.abs(node.getLongitude() - this.endLongitude);
        }
    }

    public static class HeuristicLogic extends Heuristic {


        public HeuristicLogic() {

        }

        @Override
        int h(Node n) {
            NodeLogic node = (NodeLogic) n;
            return 1;
        }
    }
}
