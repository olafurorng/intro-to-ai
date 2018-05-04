
// Re-usable parts for the Node classes
public interface Node {

    int g();

    Node getParent();

    @Override
    String toString();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}