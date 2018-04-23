import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olafurorn on 4/9/18.
 */
public class MainLogic {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List<Clause> clauses = new ArrayList<>();

        // Data file to open.
        String fileName = "data/clauses.txt";

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {    
                if (line != null && !line.isEmpty()) {
                    clauses.add(new Clause(line));
                }         
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }

        System.out.println("Clauses: " + clauses);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to find a path: " + estimatedTime + " milliseconds");
    }
}
