import entities.Node;
import entities.Road;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Main {

    public static void main(String[] args) {
        List<Road> roads = new ArrayList<>();

        roads.add(new Road(10, 70, "Vestervoldgade", 20, 50));
        roads.add(new Road(20, 50, "Vestervoldgade", 10, 70));
        roads.add(new Road(20, 50, "Vestervoldgade", 35, 35));
        roads.add(new Road(35, 35, "Vestervoldgade", 20, 50));

        roads.add(new Road(10, 70, "SktPedersStraede", 35, 80));
        roads.add(new Road(35, 80, "SktPedersStraede", 50, 90));
        roads.add(new Road(65, 100, "SktPedersStraede", 50, 90));

        roads.add(new Road(20, 50, "Studiestraede", 45, 70));
        roads.add(new Road(45, 70, "Studiestraede", 70, 85));

        roads.add(new Road(55, 55, "Vestergade", 35, 35));
        roads.add(new Road(80, 70, "Vestergade", 55, 55));

        roads.add(new Road(60, 150, "Noerregade", 65, 110));
        roads.add(new Road(65, 110, "Noerregade", 65, 100));
        roads.add(new Road(65, 100, "Noerregade", 70, 85));
        roads.add(new Road(70, 85, "Noerregade", 80, 70));

        roads.add(new Road(45, 70, "Larsbjoernsstraede", 55, 55));
        roads.add(new Road(45, 70, "Larsbjoernsstraede", 35, 80));

        roads.add(new Road(25, 100, "TeglgaardsStraede", 35, 80));

        roads.add(new Road(50, 90, "LarslejStraede", 35, 120));

        roads.add(new Road(10, 70, "Noerrevoldgade", 25, 100));
        roads.add(new Road(25, 100, "Noerrevoldgade", 10, 70));
        roads.add(new Road(25, 100, "Noerrevoldgade", 35, 120));
        roads.add(new Road(35, 120, "Noerrevoldgade", 25, 100));
        roads.add(new Road(35, 120, "Noerrevoldgade", 60, 150));
        roads.add(new Road(60, 150, "Noerrevoldgade", 35, 120));



        int latStart = 10;
        int longStart = 70;
        int latEnd = 60;
        int longEnd = 150;


        Heureka heureka = new Heureka(roads);
        boolean pathFound = heureka.findPath(new Node(null, latStart, longStart), latEnd, longEnd);
        System.out.println();
        System.out.println("Path found: " + pathFound);
    }
}
