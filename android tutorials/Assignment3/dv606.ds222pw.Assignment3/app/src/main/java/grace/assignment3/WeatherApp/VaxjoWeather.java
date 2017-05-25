package grace.assignment3.WeatherApp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import grace.assignment3.MainList;
import grace.assignment3.R;

/**
 * This is a first prototype for a weather app. It is currently
 * only downloading weather data for Växjö.
 *
 * This activity downloads weather data and constructs a WeatherReport,
 * a data structure containing weather data for a number of periods ahead.
 *
 * The WeatherHandler is a SAX parser for the weather reports
 * (forecast.xml) produced by www.yr.no. The handler constructs
 * a WeatherReport containing meta data for a given location
 * (e.g. city, country, last updated, next update) and a sequence
 * of WeatherForecasts.
 * Each WeatherForecast represents a forecast (weather, rain, wind, etc)
 * for a given time period.
 *
 * The next task is to construct a list based GUI where each row
 * displays the weather data for a single period.
 *
 *
 * @author jlnmsi
 *
 */

public class VaxjoWeather extends AppCompatActivity {
    public static String TAG = "dv606.weather";
    URL url;
    private WeatherReport report = null;
    WeatherAdapter adapter2;
    private ArrayList<String> weather_Array1 = new ArrayList<>();
    private ArrayList<String> weather_Array2 = new ArrayList<>();
    private ArrayList<String> weather_Array3 = new ArrayList<>();
    private ArrayList<String> weather_Array4 = new ArrayList<>();
    ListView listView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");

        if (city==null)
            city="Växjö";
        this.setTitle("Weather in " + city);

        if (city.equalsIgnoreCase("Växjö")) {
            System.out.println("Växjö");
            try {
                url = new URL("http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else if (city.equalsIgnoreCase("Stockholm")) {
            System.out.println("Stockholm");
            try {
                url = new URL("http://www.yr.no/place/Sweden/Stockholm/Stockholm/forecast.xml");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (city.equalsIgnoreCase("Göteborg")) {
            System.out.println("Göteborg");
            try {
                url = new URL("http://www.yr.no/sted/Sverige/V%C3%A4stra_G%C3%B6taland/G%C3%B6teborg/varsel.xml");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                url = new URL("http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        // checking if airplane mode is off (internet connection needed to download data)
        if (isNetworkAvailable() == true) {
            new WeatherRetriever().execute(url);

            // Initialize the layout
            setContentView(R.layout.weather_main);
            // Initialize the toolbar
            Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);

            listView = (ListView) findViewById(R.id.weather_list);
            adapter2 = new WeatherAdapter(this, R.layout.weather_forecast_list_item, weather_Array1, weather_Array2,weather_Array3, weather_Array4);
        } else {
            Toast.makeText(VaxjoWeather.this,"The app couldn't connect to the internet. " +
                    "Please connect to the internet and " +
                    "relaunch the application!", Toast.LENGTH_LONG).show();
        }
    }

    // http://stackoverflow.com/questions/8503553/check-if-android-phone-has-access-to-internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.country_goteborg:

                try {
                    if (isNetworkAvailable() == true) {
                        this.setTitle("Göteborg Weather");
                        url = new URL("http://www.yr.no/sted/Sverige/V%C3%A4stra_G%C3%B6taland/G%C3%B6teborg/varsel.xml");
                        AsyncTask task = new WeatherRetriever().execute(url);
                    } else {
                        System.out.println("App couldn't connect to the internet, please connect and relaunch the app");
                        Toast.makeText(VaxjoWeather.this,"The app couldn't connect to the internet. " +
                                "Please connect to the internet and " +
                                "relaunch the application!", Toast.LENGTH_LONG).show();
                    }
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
                adapter2.notifyDataSetChanged();
                return true;
            case R.id.country_vaxjo:

                try {
                    if (isNetworkAvailable() == true) {
                        this.setTitle("Växjö Weather");
                        url = new URL("http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
                        AsyncTask task = new WeatherRetriever().execute(url);
                    } else {
                        System.out.println("App couldn't connect to the internet, please connect and relaunch the app");
                        Toast.makeText(VaxjoWeather.this,"The app couldn't connect to the internet. " +
                                "Please connect to the internet and " +
                                "relaunch the application!", Toast.LENGTH_LONG).show();
                    }
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
                adapter2.notifyDataSetChanged();
                return true;
            case R.id.country_stockholm:

                try {
                    if (isNetworkAvailable() == true) {
                        this.setTitle("Stockholm Weather");
                        url = new URL("http://www.yr.no/sted/Sverige/Stockholm/Stockholm/varsel.xml");
                        AsyncTask task = new WeatherRetriever().execute(url);
                    } else {
                        System.out.println("App couldn't connect to the internet, please connect and relaunch the app");
                        Toast.makeText(VaxjoWeather.this,"The app couldn't connect to the internet. " +
                                "Please connect to the internet and " +
                                "relaunch the application!", Toast.LENGTH_LONG).show();
                    }
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
                adapter2.notifyDataSetChanged();
                return true;

            case android.R.id.home:  // Predefined icon ID

                // app icon in action bar clicked ==>  go home
                Intent intent2 = new Intent(this, MainList.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                return true;

            //case android.R.id.sort_menu_item:

            //  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void printReportToLog() {
        if (this.report != null) {
        	/* Print location meta data */
            Log.i(TAG, report.toString());

        	/* Print forecasts */
            int count = 0;
            // for each weatherForecast in report
            for (WeatherForecast forecast : report) {
                count++;
                //Log.i(TAG, "Forecast #" + count);
                //Log.i(TAG, forecast.toString());
                }
        } else {
            Log.e(TAG, "Weather report has not been loaded.");
        }
    }

    // design the list item view with multiple items in a row
    //http://www.vogella.com/tutorials/AndroidListView/article.html
    public class WeatherAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> values1;
        private final ArrayList<String> values2;
        private final ArrayList<String> values3;
        private final ArrayList<String> values4;

        public WeatherAdapter(Context context, int textViewResourceId, ArrayList<String> values1, ArrayList<String> values2, ArrayList<String> values3, ArrayList<String> values4) {
            super(context, textViewResourceId, values1);
            this.context = context;
            this.values1 = values1; // date
            this.values2 = values2; // time
            this.values3 = values3; // weather
            this.values4 = values4; // temperature
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.weather_forecast_list_item, parent, false);

            TextView firstLine = (TextView) rowView.findViewById(R.id.firstLine);
            TextView secondLine = (TextView) rowView.findViewById(R.id.secondLine);
            TextView thirdLine = (TextView) rowView.findViewById(R.id.thirdLine);
            TextView fourthLine = (TextView) rowView.findViewById(R.id.fourthLine);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.weather_icon);

                // gets the data item in the position
                String s = values3.get(position);

                if (s.startsWith("Lettskyet") || s.startsWith("Delvis skyet") || s.toLowerCase().contains("fair")|| s.toLowerCase().contains(
                        "partly cloudy")) {
                    imageView.setImageResource(R.drawable.sun_cloud);
                } else if (s.toLowerCase().contains("sol") || s.toLowerCase().contains("klarvær") || s.toLowerCase().contains("clear sky")|| s.toLowerCase().contains(
                        "sunny")) {
                    imageView.setImageResource(R.drawable.sunny);
                } else if (s.toLowerCase().contains("skyet") || s.toLowerCase().contains("cloud")) {
                    imageView.setImageResource(R.drawable.cloud);
                } else if (s.toLowerCase().contains("regn") || s.toLowerCase().contains("rain")) {
                    imageView.setImageResource(R.drawable.rain);
                } else if (s.toLowerCase().contains("snø") || s.toLowerCase().contains("snow")) {
                    imageView.setImageResource(R.drawable.snow);
                } else {
                    imageView.setImageResource(R.drawable.sun_cloud);
                }

                // Populate the data into the template view using the data object
                firstLine.setText(values1.get(position));
                secondLine.setText(values2.get(position));
                thirdLine.setText(values3.get(position));
                fourthLine.setText(values4.get(position));

            // Return the completed view to render on screen
            return rowView;
        }
    }

    private class WeatherRetriever extends AsyncTask<URL, Void, WeatherReport> {
        /**
         * invoked on the background thread immediately after onPreExecute() finishes
         * executing. This step is used to perform background computation that can take a
         * long time
         * */
        protected WeatherReport doInBackground(URL... urls) {
            try {
                return WeatherHandler.getWeatherReport(urls[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /*
        * This method is used to display any form of progress in the user interface while
        * the background computation is still executing. For instance, it can be used
        * to animate a progress bar or show logs in a text field.
        * */
        protected void onProgressUpdate(Void... progress) {

        }

        // invoked on the UI thread after the background computation finishes
        protected void onPostExecute(WeatherReport result) {
            Toast.makeText(getApplicationContext(), "WeatherRetriever task finished", Toast.LENGTH_LONG).show();

            weather_Array1.clear();
            weather_Array2.clear();
            weather_Array3.clear();
            weather_Array4.clear();

            report = result; // populates the whole weather report
            if (report != null) {
                for (WeatherForecast forecast : report) {
                    //first line in the row
                    weather_Array1.add("Date " + forecast.getStartYYMMDD());
                    //2. line in the row
                    weather_Array2.add("From: " + forecast.getStartHHMM() + " To: " + forecast.getEndHHMM());
                    //3. line in the row
                    weather_Array3.add(forecast.getWeatherName() + "");
                    //4. line in the row
                    weather_Array4.add("Temperature: " + forecast.getTemperature() + " Degrees");

                    listView.setAdapter(adapter2);
                }
            } else {
                Toast.makeText(VaxjoWeather.this,"Weather Report not found. " +
                        "Please try again.", Toast.LENGTH_LONG).show();
            }
                printReportToLog();

                for (String output : weather_Array1) {
                    //Log.e("WeatherArray", output);
                }
        }
    }
}
