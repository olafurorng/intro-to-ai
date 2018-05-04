import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NodeLogic implements Node {

    private NodeLogic parent;
    private Set<String> kownLiterals;
    private Clause lastResolvedClause;

    private int g;

    public NodeLogic(NodeLogic parent, Clause lastResolvedClause, Set<String> newLiterals) {
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

    public Set<String> getKownLiterals() {
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

        return kownLiterals.equals(nodeLogic.kownLiterals);
    }

    @Override
    public int hashCode() {
        return kownLiterals.hashCode();
    }
}