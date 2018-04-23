import java.util.List;

/**
 * Created by olafurorn on 4/9/18.
 */
public class NodeLogic implements Node {

    private NodeLogic parent;
    private List<Character> literals;

    private int g;

    public NodeLogic(NodeLogic parent, List<Character> newLiterals) {
        this.parent = parent;
        this.literals = newLiterals;

        if (parent == null) {
            this.g = 0;
        } else {
            this.literals.addAll(parent.getLiterals());
            this.g = parent.g() + 1;
        }
    }

    public int g() {
        return this.g;
    }

    public List<Character> getLiterals() {
        return literals;
    }

    public NodeLogic getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "NodeLogic{" +
                "parent=" + parent +
                ", literals=" + literals +
                ", g=" + g +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeLogic nodeLogic = (NodeLogic) o;

        if (g != nodeLogic.g) return false;
        if (parent != null ? !parent.equals(nodeLogic.parent) : nodeLogic.parent != null) return false;
        return literals.equals(nodeLogic.literals);
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + literals.hashCode();
        result = 31 * result + g;
        return result;
    }
}
