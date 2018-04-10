/**
 * Created by olafurorn on 4/9/18.
 */
public class Node {

    private Node parent;
    private int latitude;
    private int longitude;

    private int g;

    public Node(Node parent, int latitude, int longitude) {
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

    public Node getParent() {
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

        Node node = (Node) o;

        if (latitude != node.latitude) return false;
        if (longitude != node.longitude) return false;
        return parent != null ? parent.equals(node.parent) : node.parent == null;
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + latitude;
        result = 31 * result + longitude;
        return result;
    }
}
