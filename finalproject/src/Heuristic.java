

import java.util.Comparator;

/**
 * Created by olafurorn on 4/10/18.
 */
public class Heuristic implements Comparator<Node> {

    private int endLatitude;
    private int endLongitude;

    public Heuristic(int endLatitude, int endLongitude) {
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
    }

    private int h(Node n) {
        return Math.abs(n.getLatitude() - this.endLatitude) + Math.abs(n.getLongitude() - this.endLongitude);
    }


    private int f(Node n) {
        return n.g() + this.h(n);
    }

    @Override
    public int compare(Node n1, Node n2) {
        return this.f(n1) - this.f(n2);
    }
}
