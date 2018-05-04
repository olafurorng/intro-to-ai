import java.io.*;
import java.util.*;

public class MainRoute {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        List<Road> roads = new ArrayList<>();

        // Data file to open.
        String fileName = "data/city.txt";

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
 
            // Remove duplicate lines
            HashSet<String> lines = new HashSet<String>();
            while((line = bufferedReader.readLine()) != null) {    
                if (line != null && !line.isEmpty()) {
                    lines.add(line);
                }         
            }  
            for (String form: lines) {
                String[] splitted = form.split("\\s+");
                    roads.add(new Road(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]), splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4])));
            }

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }

        int latStart = 25;
        int longStart = 100;
        int latEnd = 50;
        int longEnd = 90;

        Heureka heureka = new Heureka();
        String pathFound = heureka.findRoutePath(roads, new NodeRoute(null, latStart, longStart, ""), latEnd, longEnd);
        System.out.println();
        System.out.println("Path found: " + pathFound);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to find a path: " + estimatedTime + " milliseconds");
    }
}