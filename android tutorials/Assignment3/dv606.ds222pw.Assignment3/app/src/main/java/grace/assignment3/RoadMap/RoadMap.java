package grace.assignment3.RoadMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.net.MalformedURLException;
import java.net.URL;

import grace.assignment3.MainList;
import grace.assignment3.R;
import grace.assignment3.WeatherApp.VaxjoWeather;

/**
 * Created by David Szabo, 26/03/17
 */
public class RoadMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RouteReport routeReport = null;
    private URL urls[] = new URL[3];
    private int urlSelect=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.road_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // An XML based menu specification.
        // See res/menu/action_menu.xml for details
        getMenuInflater().inflate(R.menu.city_map, menu);
        return true;
    }
    /**
     * From Android Course at Linneaus University, 2017.03.25.
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = "";

        switch (item.getItemId()) {
            case R.id.zoom_in:
                msg = "Zooming in";
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.zoom_out:
                msg = "Zooming out";
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;
            case R.id.vaxjö_Stockholm:
                msg = "To Stockholm";
                urlSelect = 0;
                if (isNetworkAvailable()) {
                    new RoadMapRetreiver().execute(urls[urlSelect]);
                }
                else {
                    Toast.makeText(RoadMap.this,"The app couldn't connect to the internet. " +
                            "Please connect to the internet and " +
                            "relaunch the application!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.vaxjö_Coppenhagen:
                msg = "To Coppenhagen";
                urlSelect = 1;
                if (isNetworkAvailable()) {
                    new RoadMapRetreiver().execute(urls[urlSelect]);
                }
                else {
                    Toast.makeText(RoadMap.this,"The app couldn't connect to the internet. " +
                            "Please connect to the internet and " +
                            "relaunch the application!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.vaxjö_Odessa:
                msg = "To Odessa";
                urlSelect=2;
                if (isNetworkAvailable()) {
                    new RoadMapRetreiver().execute(urls[urlSelect]);
                }
                else {
                    Toast.makeText(RoadMap.this,"The app couldn't connect to the internet. " +
                            "Please connect to the internet and " +
                            "relaunch the application!", Toast.LENGTH_LONG).show();
                }
                break;
            case android.R.id.home:  // Predefined icon ID
                // app icon in action bar clicked ==>  go home
                Intent intent = new Intent(this, MainList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.show();

        return true;
    }

    // http://stackoverflow.com/questions/8503553/check-if-android-phone-has-access-to-internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    /**
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            urls[0] = new URL("http://cs.lnu.se/android/VaxjoToStockholm.kml");
            urls[1] = new URL("http://cs.lnu.se/android/VaxjoToCopenhagen.kml");
            urls[2] = new URL("http://cs.lnu.se/android/VaxjoToOdessa.kml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (isNetworkAvailable()) {
            new RoadMapRetreiver().execute(urls[urlSelect]);
        }
        else {
            Toast.makeText(RoadMap.this,"The app couldn't connect to the internet. " +
                    "Please connect to the internet and " +
                    "relaunch the application!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Print the routeReports
     */
    private void printToLog() {
        if (routeReport != null) {
        	    /* Print location meta data */
            Log.i("TAG", routeReport.toString());

        	    /* Print routeCordinates */
            int count = 0;
            // for each routeCordinates in routeReport
            for (RouteCoordinate routeCoordinate : routeReport) {
                count++;
                Log.i("TAG", "routeCoordinate #" + count);
                Log.i("TAG", routeCoordinate.toString());
            }
        } else {
            Log.e("TAG", "Route report has not been loaded.");
        }
    }

    private class RoadMapRetreiver extends AsyncTask<URL, Void, RouteReport> {

        @Override
        protected RouteReport doInBackground(URL... urls) {
            try {
                return RoadMapHandler.getRoadMapObj(urls[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Add a marker to the start and end position and draw a polyLine in the intermediate routes
         * Also, adjust the camera position
         * @param report
         */
        @Override
        protected void onPostExecute(RouteReport report) {

            routeReport = report;

            printToLog();

            // save the starting posiiton
            LatLng start = routeReport.firstElement().getRoute().get(0);
            // save the end posiiton
            LatLng end = routeReport.firstElement().getRoute().get(routeReport.firstElement().getRoute().size()-1);

            System.out.println("routeCoordinate  1st element: " + routeReport.firstElement().getRoute().get(0).toString());
            System.out.println("routeCoordinate last element: " + routeReport.firstElement().getRoute().get(routeReport.firstElement().getRoute().size()-1).toString());

            Marker startMarker = mMap.addMarker(new MarkerOptions()
                    .position(start));

            Marker endMarker = mMap.addMarker(new MarkerOptions()
                    .position(end));

            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .width(5)
                    .color(Color.RED));
            line.setPoints(routeReport.firstElement().getRoute());


            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(start);
            builder.include(end);
            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
            mMap.animateCamera(cu);
        }
    }
}
