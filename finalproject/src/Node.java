/**
 * Created by olafurorn on 4/9/18.
 */
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
