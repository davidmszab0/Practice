package grace.assignment3.RoadMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by David Szabo on 25/03/17.
 * Save each routeCoordinate LatLng arrayList in a RouteReport, which is an arrayList of routeCoordinates
 * Source: Linnaues university, Android for Javaprogrammers course
 */

public class RouteReport implements Iterable<RouteCoordinate>{

    private List<RouteCoordinate> routeCoordinates = new ArrayList<RouteCoordinate>();
    private String name;

    public RouteReport() {
    }

    public String getName() {return name;}

    public List<RouteCoordinate> getRouteCoordinates() {
        return routeCoordinates;
    }

    public RouteCoordinate firstElement() {
        return routeCoordinates.get(routeCoordinates.size()-3);
    }

    public void printArray() {
        for (int x=0; x<routeCoordinates.size(); x++)
            System.out.println(routeCoordinates.get(x));
    }
    public int arraySize() {
        return routeCoordinates.size();
    }

    @Override
    public Iterator<RouteCoordinate> iterator() { return routeCoordinates.iterator();}

    // ---------- SETTERS -----------
    public void setName(String name) {
        this.name=name;
    }

    void addRouteCoordinate(RouteCoordinate routeCoordinate) { routeCoordinates.add(routeCoordinate); }


    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("*** Route Report ***");
        buf.append("\nDirection: "+getName());
        return buf.toString();
    }

}
