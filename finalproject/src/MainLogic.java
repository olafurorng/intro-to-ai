import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by olafurorn on 4/9/18.
 */
public class MainLogic {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();


        List<Clause> clauses = new ArrayList<>();

        // Data file to open.
        String fileName = "data/clausetest2.txt";

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);


            while ((line = bufferedReader.readLine()) != null) {
                if (line != null && !line.isEmpty()) {
                    clauses.add(new Clause(line));
                }
            }
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            return;
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            return;
        }

        for (Clause clause : clauses) {
            System.out.println("Clauses hash left: " + clause.getCnfHashLeft());
            System.out.println("Clauses hash right: " + clause.getCnfHashRight());
            // Possible remove duplicates of clauses containing each other
        }

        // iterate and find the clause which doesn't have any literals in left CNF hash map, i.e. literals which are known
        Set<String> knownLiterals = new HashSet<>();
        for (Clause clause : clauses) {
            if (clause.getCnfHashLeft().isEmpty()) {
                for (String literal : clause.getCnfHashRight().keySet()) {
                    knownLiterals.add(literal);
                }
            }
        }

        if (knownLiterals.isEmpty()) {
            throw new IllegalStateException("Couldn't find known literals");
        }

        Heureka heureka = new Heureka();
        String resolvedClauses = heureka.resolveFromKb(clauses, "q", knownLiterals);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to find a path: " + estimatedTime + " milliseconds");
        System.out.println();
        System.out.println("Order of resolved clauses: " + resolvedClauses);
    }
}
