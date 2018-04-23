import java.util.HashMap;

public class Clause {

    private String originalForm;
    private static HashMap<Character, Boolean> cnfHash = new HashMap<Character, Boolean>();

    public Clause(String originalForm) {
        this.originalForm = originalForm;

        String[] lessArray = key.split("<");

        if (lessArray.length == 1) {
            String[] spaceArray = lessArray[0].split(" ");

            for (String letter : spaceArray) {
                cnfHash.put(letter.charAt(0), true);
            }
        }
        else {
            String[] leftSpaceArray = lessArray[0].split(" ");
            String[] RightSpaceArray = lessArray[1].split(" ");

            for (String letter : leftSpaceArray) {
                cnfHash.put(letter.charAt(0), true);
            }

            for (String letter : RightSpaceArray) {
                cnfHash.put(letter.charAt(0), false);
            }
        }
    }

    public String getOriginalForm() {
        return originalForm;
    }

    public HashMap getCnfHash() {
        return cnfHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clause clause = (Clause) o;

        return originalForm.equals(clause.originalForm);
    }

    @Override
    public int hashCode() {
        return originalForm.hashCode();
    }

    @Override
    public String toString() {
        return "Clause{" +
                "originalForm='" + originalForm + '\'' +
                '}';
    }
}
