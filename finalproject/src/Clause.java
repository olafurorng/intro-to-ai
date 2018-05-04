import java.util.HashMap;
import java.util.Map;

public class Clause {

    private String originalForm;
    private HashMap<String, Boolean> cnfHashLeft = new HashMap<String, Boolean>();
    private HashMap<String, Boolean> cnfHashRight = new HashMap<String, Boolean>();

    public Clause(String originalForm) {
        this.originalForm = originalForm;

        boolean ifFormat = originalForm.indexOf("if") != -1 ? true : false;
        boolean lessFormat = originalForm.indexOf("<") != -1 ? true : false;

        // Change to CNF form
        if (!ifFormat && !lessFormat) {
            String[] spaceArray = originalForm.split(" ");

            for (String letter : spaceArray) {
                if (letter != null && !letter.isEmpty()) {    
                    if (letter.substring(0, 1) == "!") {
                        cnfHashRight.put(letter.substring(1), false);
                    }
                    else {
                        cnfHashRight.put(letter, true);
                    }
                }
            }
        }
        else {
            String[] lessArray = {};

            if (lessFormat) {
                lessArray = originalForm.split("<");
            }
            else {
                lessArray = originalForm.split("if");
            }

            String[] leftSpaceArray = lessArray[0].split(" ");
            String[] RightSpaceArray = lessArray[1].split(" ");

            for (String letter : leftSpaceArray) {
                if (letter != null && !letter.isEmpty()) {  
                    if (letter.substring(0, 1) == "!") {
                        cnfHashLeft.put(letter.substring(1), false);
                    }
                    else {
                        cnfHashLeft.put(letter, true);
                    }  
                }
            }

            for (String letter : RightSpaceArray) {  
                if (letter != null && !letter.isEmpty()) {    
                    if (letter.substring(0, 1) == "!") {
                        cnfHashRight.put(letter.substring(1), true);
                    }
                    else {
                        cnfHashRight.put(letter, false);
                    }
                }
            }
        }
    }

    public String getOriginalForm() {
        return originalForm;
    }

    public HashMap<String, Boolean> getCnfHashLeft() {
        return cnfHashLeft;
    }

    public HashMap<String, Boolean> getCnfHashRight() {
        return cnfHashRight;
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
        return originalForm;
    }
}