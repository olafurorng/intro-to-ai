/**
 * Created by olafurorn on 4/9/18.
 */
public class NodeRoute implements Node {

    private NodeRoute parent;
    private int latitude;
    private int longitude;

    private int g;

    public NodeRoute(NodeRoute parent, int latitude, int longitude) {
        this.parent = parent;
        this.latitude = latitude;
        this.longitude = longitude;

        if (parent == null) {
            this.g = 0;
        } else {
            this.g = parent.g() + 1;
        }
    }


    public int g() {
        return this.g;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public NodeRoute getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeRoute nodeRoute = (NodeRoute) o;

        if (latitude != nodeRoute.latitude) return false;
        return longitude == nodeRoute.longitude;
    }

    @Override
    public int hashCode() {
        int result = latitude;
        result = 31 * result + longitude;
        return result;
    }
}
