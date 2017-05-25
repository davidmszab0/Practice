package grace.assignment3.RoadMap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by David Szabo on 26/03/17.
 * Saving the LatLng positions in an ArrayList
 * Source: Linnaues university, Android for Javaprogrammers course
 */

public class RouteCoordinate {

    private String coordinate;
    private double latitude, longitude;
    private ArrayList<LatLng> route = new ArrayList<LatLng>();

    public String getCoordinate() {
        return coordinate;
    }
    public ArrayList<LatLng> getRoute() { return route; }

    public void setCoordinate(String coordinate) {
        this.coordinate=coordinate;
    }
    public void setRoute(ArrayList<LatLng> latLng) { this.route=latLng; }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("RouteCoordinate is: "+ getRoute().toString());
        return buf.toString();
    }
}
