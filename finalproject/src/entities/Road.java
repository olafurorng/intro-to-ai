package entities;

/**
 * Created by olafurorn on 4/9/18.
 */
public class Road {

    private int latStart;
    private int longStart;
    private String name;
    private int latEnd;
    private int longEnd;

    public Road(int latStart, int longStart, String name, int latEnd, int longEnd) {
        this.latStart = latStart;
        this.longStart = longStart;
        this.name = name;
        this.latEnd = latEnd;
        this.longEnd = longEnd;
    }

    public int getLatStart() {
        return latStart;
    }

    public int getLongStart() {
        return longStart;
    }

    public String getName() {
        return name;
    }

    public int getLatEnd() {
        return latEnd;
    }

    public int getLongEnd() {
        return longEnd;
    }

    @Override
    public String toString() {
        return "Road{" +
                "latStart=" + latStart +
                ", longStart=" + longStart +
                ", name='" + name + '\'' +
                ", latEnd=" + latEnd +
                ", longEnd=" + longEnd +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Road road = (Road) o;

        if (latStart != road.latStart) return false;
        if (longStart != road.longStart) return false;
        if (latEnd != road.latEnd) return false;
        if (longEnd != road.longEnd) return false;
        return name != null ? name.equals(road.name) : road.name == null;
    }

}
