package grace.assignment3.CityMap;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;
import grace.assignment3.MainList;
import grace.assignment3.R;
import static grace.assignment3.R.id.map;

/**
 * created by David Szabo
 * This class reads the assets/location.txt file, puts markers to the corresponding cities.
 * Then it calculates the distance between the nearest city and the center of the map.
 * */
public class CityMap extends AppCompatActivity implements
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveCanceledListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();
    Circle centerCircle;
    LatLng centerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveCanceledListener(this);


        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {


                readLocations(mMap);
                showAllMarkers(mMarkerArray, mMap);

                centerPosition = mMap.getCameraPosition().target;
                centerCircle = mMap.addCircle(new CircleOptions()
                        .center(centerPosition)
                        .radius(7000)
                        .strokeColor(Color.RED)
                        .fillColor(Color.BLUE)
                        .strokeWidth(5)
                );

                Log.e("TAG", mMap.getCameraPosition().target.toString());
            }
        });
    }

    /**
     * Reading the location.txt file and setting the markers to the location of each city
     * Also, adding an onMarkerClickListener to show a toast message and marker title
     * when the user clicks on the Marker
     *
     * @param googleMap
     * */
    private void readLocations(GoogleMap googleMap) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("locations.txt"), "UTF-8"));

            String thisLine;
            while ((thisLine = reader.readLine()) != null) {
                System.out.println(thisLine);
                final String[] parts = thisLine.split(";");

                LatLng city = new LatLng(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(city)
                        .title(parts[0]));

                mMarkerArray.add(marker);
            }
            reader.close();

            // set toast and marker title to show when clicked
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow();
                    Toast.makeText(CityMap.this, marker.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                    return true;
                }
            });

        } catch (IOException e) {
            System.out.println("File Read Error");
        }
    }

    /**
     * Zooming the map to show all markers
     * Source: http://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers
     *
     * @param mMarkerArray
     * @param googleMap
     * */
    private void showAllMarkers(ArrayList<Marker> mMarkerArray, GoogleMap googleMap){

        // map zoom to show all the markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkerArray) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        googleMap.moveCamera(cu);
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

    @Override
    public void onCameraMoveStarted(int i) {
        Log.e("TAG", "camera position started to move");
    }

    /**
     * Whenever the user moves the map, the camera position moves too
     * Redraw the circle to the center of the map
     * */
    @Override
    public void onCameraMove() {

        //Log.e("TAG", "camera position moving");

        centerPosition = mMap.getCameraPosition().target;
        centerCircle.remove();

        centerCircle = mMap.addCircle(new CircleOptions()
                .center(centerPosition)
                .radius(7000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE)
                .strokeWidth(5)
        );
    }

    /**
     * When the camera is idle, calculate the distance between the closes city to the centre of the
     * map and toast the distance in km and the city name
     * */
    @Override
    public void onCameraIdle() {
        Log.e("TAG", "camera is idle");

        Location locationB=null;
        TreeMap<Float,String> treeMap =new TreeMap<Float,String>();

        if (centerPosition != null ) {

            Location locationCenter = new Location("Center");
            locationCenter.setLatitude(centerPosition.latitude);
            locationCenter.setLongitude(centerPosition.longitude);

            for (Marker marker : mMarkerArray) {
                locationB = new Location("City");
                locationB.setLatitude(marker.getPosition().latitude);
                locationB.setLongitude(marker.getPosition().longitude);
                float distanceInKmeters = locationCenter.distanceTo(locationB) / 1000;
                Log.e("TAG", "distance is: " + distanceInKmeters);
                treeMap.put(distanceInKmeters,marker.getTitle());
            }
            Toast.makeText(CityMap.this,
                    "Distance to the Center is: " + treeMap.firstKey() + " km from " + treeMap.get(treeMap.firstKey()),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCameraMoveCanceled() {
        Log.e("TAG", "camera move is cancelled");

    }
}
