import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by olafurorn on 4/9/18.
 */
public class NodeLogic implements Node {

    private NodeLogic parent;
    private Set<Character> kownLiterals;
    private Clause lastResolvedClause;

    private int g;

    public NodeLogic(NodeLogic parent, Clause lastResolvedClause, List<Character> newLiterals) {
        this.parent = parent;
        this.lastResolvedClause = lastResolvedClause;
        this.kownLiterals = new HashSet<>();
        this.kownLiterals.addAll(newLiterals);

        if (parent == null) {
            this.g = 0;
        } else {
            this.kownLiterals.addAll(parent.getKownLiterals());
            this.g = parent.g() + 1;
        }
    }

    public int g() {
        return this.g;
    }

    public Set<Character> getKownLiterals() {
        return kownLiterals;
    }

    public Clause getLastResolvedClause() {
        return lastResolvedClause;
    }

    public NodeLogic getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "NodeLogic{" +
                "parent=" + parent +
                ", kownLiterals=" + kownLiterals +
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
        return kownLiterals.equals(nodeLogic.kownLiterals);
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + kownLiterals.hashCode();
        result = 31 * result + g;
        return result;
    }
}
