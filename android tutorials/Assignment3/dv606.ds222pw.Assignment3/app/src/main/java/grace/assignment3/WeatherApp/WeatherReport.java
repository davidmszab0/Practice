package grace.assignment3.WeatherApp;

/**
 * Created by root on 2016-09-07.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author jonasl
 *
 * The WeatherReport is a data structure containing weather data. It contains meta data
 * for a given location (e.g. city, country, last updated, next update) and a
 * sequence of WeatherForecasts.
 */
public class WeatherReport implements Iterable<WeatherForecast> {
    private List<WeatherForecast> forecasts = new ArrayList<WeatherForecast>();
    private String city, country;
    private Date last_updated, next_update;
    private double latitude, longitude;
    private int altitude;

    public WeatherReport(String city, String country) {
        this.city = city;
        this.country = country;
    }

    /*
     * Access methods
     * ---------------- GETTERS ----------------
     */
    public String getCity() {return city;}
    public String getCountry() {return country;}
    public String getLastUpdated() {return TimeUtils.getHHMM(last_updated);}
    public String getNextUpdate() {return TimeUtils.getHHMM(next_update);}
    public double getLongitude() {return longitude;}
    public double getLatitude() {return latitude;}
    public int getAltitude() {return altitude;}
    public List<WeatherForecast> getForecasts() {
        return forecasts;
    }
    @Override
    public Iterator<WeatherForecast> iterator() {return forecasts.iterator();}

    /*
     * Diagnostics
     */
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("*** Weather Report ***");
        buf.append("\nLocation: "+getCity()+", "+getCountry());
        buf.append("\nAlt: "+getAltitude()+"m, Lat: "+getLatitude()+", Long: "+getLongitude());
        buf.append("\nLast Updated: "+getLastUpdated() );
        buf.append("\nNext Update: "+getNextUpdate() );

        return buf.toString();
    }

    /*
     * Methods used by WeatherHandler to build forecast
     *
     *  ---------------- SETTERS ----------------
     * */
    void setLocation(String lat, String lng, String alt) {
        altitude = Integer.parseInt(alt);
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);
    }

    void setLastUpdated(String last) {
        last_updated = TimeUtils.getDate(last);
    }

    void setNextUpdate(String next) {
        next_update = TimeUtils.getDate(next);
    }

    void addForecast(WeatherForecast forecast) { forecasts.add(forecast); }

    void setCity(String name) {this.city = name; }

    void setCountry(String country) {this.country = country; }
}

