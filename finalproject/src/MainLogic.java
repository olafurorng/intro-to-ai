import java.io.*;
import java.util.*;

/**
 * Created by olafurorn on 4/9/18.
 */
public class MainLogic {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List<Clause> clauses = new ArrayList<>();

        // Data file to open.
        String fileName = "data/clausetest2_no_solution.txt";

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Remove duplicate lines
            HashSet<String> lines = new HashSet<String>();
            while ((line = bufferedReader.readLine()) != null) {
                if (line != null && !line.isEmpty()) {
                    lines.add(line);
                }
            }
            for (String form: lines) {
                clauses.add(new Clause(form));
            }

            bufferedReader.close();  
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            return;
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            return;
        }

        // Remove duplicate clauses
        List<Clause> clausesCopy = new ArrayList<>(clauses);

        for (Clause outerClause : clausesCopy) {
            for (Clause innerClause : clausesCopy) {
                if (!Objects.equals(outerClause.getOriginalForm(),innerClause.getOriginalForm())) {
                    if (outerClause.getCnfHashLeft().equals(innerClause.getCnfHashLeft())) {
                        if (innerClause.getCnfHashRight().entrySet().containsAll(outerClause.getCnfHashRight().entrySet())) {
                            clauses.remove(innerClause);
                        }            
                    }
                    if (outerClause.getCnfHashRight().equals(innerClause.getCnfHashRight())) {
                        if (innerClause.getCnfHashLeft().entrySet().containsAll(outerClause.getCnfHashLeft().entrySet())) {
                            clauses.remove(outerClause);
                        }            
                    }
                }
            }
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
        String resolvedClauses = heureka.resolveFromKb(clauses, "a", knownLiterals);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to find a path: " + estimatedTime + " milliseconds");
        System.out.println();
        System.out.println("Order of resolved clauses: " + resolvedClauses);
    }
}
