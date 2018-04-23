import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by olafurorn on 4/9/18.
 */
public class MainLogic {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        /**********************************
         * HARDCODED KNOWLEDGE BASE
         */

        List<Clause> clauses = new ArrayList<>();

//        HashMap<Character, Boolean> clauseMap1 = new HashMap<>();
//        clauseMap1.put('d', true);
//        clauses.add(new Clause(null, clauseMap1));
//
//        HashMap<Character, Boolean> clauseMap2 = new HashMap<>();
//        clauseMap2.put('b', true);
//        clauseMap2.put('c', true);
//        clauseMap2.put('d', false);
//        clauses.add(new Clause( null, clauseMap2));
//
//        HashMap<Character, Boolean> clauseMap3 = new HashMap<>();
//        clauseMap3.put('b', true);
//        clauseMap3.put('c', false);
//        clauseMap3.put('d', false);
//        clauses.add(new Clause(null, clauseMap3));
//
//        HashMap<Character, Boolean> clauseMap4 = new HashMap<>();
//        clauseMap4.put('a', true);
//        clauseMap4.put('b', false);
//        clauses.add(new Clause(null, clauseMap4));


        Character goal = 'a';


        /**
         * END OF HARDCODED KNOWLEDGE BASE
         *********************************/


//            while((line = bufferedReader.readLine()) != null) {
//                if (line != null && !line.isEmpty()) {
//                    clauses.add(new Clause(line));
//                }
//            }

        for (Clause clause : clauses) {
            System.out.println("Clauses hash: " + clause.getCnfHash());
        }

        // iterate and find the only positive literals which are known literals
        List<Character> knownLiterals = new ArrayList<>();
        for (Clause clause : clauses) {
            HashMap<Character, Boolean> cnf = clause.getCnfHash();
            boolean clauseFreeFromNegativeLiterals = true;
            for (Character literal : cnf.keySet()) {
                if (!cnf.get(literal)) {
                    clauseFreeFromNegativeLiterals = false;
                    break;
                }
            }

            if (clauseFreeFromNegativeLiterals) {
                for (Character literal : cnf.keySet()) {
                    knownLiterals.add(literal);
                }
            }
        }

        if (knownLiterals.isEmpty()) {
            throw new IllegalStateException("Couldn't find known literals");
        }

        Heureka heureka = new Heureka();
        String resolvedClauses = heureka.resolveFromKb(clauses, goal, knownLiterals);
        System.out.println();
        System.out.println("Order of resolved clauses: " + resolvedClauses);
    }
}
