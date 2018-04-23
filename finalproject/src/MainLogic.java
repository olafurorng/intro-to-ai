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
        Boolean doRoads = false;

        List<Road> roads = new ArrayList<>();

        // Data file to open.
        String fileName = "data/clauses.txt";

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {    
                if (line != null && !line.isEmpty()) {

                    String[] splitted = line.split("\\s+");
                    if (doRoads) {
                        roads.add(new Road(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4])));
                    }
                    else {
            
                    }
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

        if (doRoads) {
            int latStart = 2;
            int longStart = 2;
            int latEnd = 5;
            int longEnd = 8;
    
            Heureka heureka = new Heureka();
            String pathFound = heureka.findRoutePath(roads, new NodeRoute(null, latStart, longStart), latEnd, longEnd);
            System.out.println();
            System.out.println("Path found: " + pathFound);
        }
        else {

        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to find a path: " + estimatedTime + " milliseconds");
    }
}
