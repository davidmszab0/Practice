package grace.assignment3.WeatherWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import grace.assignment3.R;
import grace.assignment3.WeatherApp.VaxjoWeather;
import grace.assignment3.WeatherApp.WeatherForecast;
import grace.assignment3.WeatherApp.WeatherHandler;
import grace.assignment3.WeatherApp.WeatherReport;

/**
 * https://developer.android.com/guide/topics/appwidgets/index.html
 * https://developer.android.com/guide/practices/ui_guidelines/widget_design.html
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is partly my, David Szabo's work!
 * */
public class WeatherWidget extends AppWidgetProvider {
	
	private static final String LOG = "widget.Weather";
    private static URL url;
    private WeatherReport report = null;
    private static RemoteViews views;
    private AppWidgetManager appWidgetManager;
    private int appWidgetId;
    private String city;

    // Fixme "Sometimes it takes 3-4 times to add the widget, because it doesn't load the PickCity"

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.w(LOG, "onReceive method called: " + intent.getAction());

        if (intent.getExtras() != null) {
            int id = intent.getIntExtra("id", 0);
            if (intent.getAction().equalsIgnoreCase("UPDATE_WEATHER")
                    || intent.getAction().equalsIgnoreCase(
                    "android.appwidget.action.APPWIDGET_ENABLED")) {
                if (appWidgetManager == null)
                    appWidgetManager = AppWidgetManager.getInstance(context);

                int[] appWidgetIds = { id };
                onUpdate(context, appWidgetManager, appWidgetIds);
            }
        }
    }

    // updateMillis in AppWidgetProvider xml calls this method
	// when you click on the button, fetch data from the VaxjoWeather and update the widget -> onReceive() ?
	// when you click on the widget, launch VaxjoWeather
    // maybe start a service so the widget doesn't get an error if the web request is late
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
						 int[] appWidgetIds) {
		Log.w(LOG, "onUpdate method called: " + Arrays.toString(appWidgetIds));

        this.appWidgetManager = appWidgetManager;

        Intent serviceIntent = new Intent(context.getApplicationContext(), RefreshService.class);
        serviceIntent.putExtra("appWidgetIds", appWidgetIds);
        context.startService(serviceIntent);
	}
    /**
     * When the user deletes the widget, delete the preference associated with it.
     */

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            WeatherWidgetConfiguration.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    /**
     * called on restart of device
     */
    @Override
    public void onEnabled(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] allIds = manager.getAppWidgetIds(new ComponentName(context, WidgetActivity.class));
        onUpdate(context, manager, allIds);
        super.onEnabled(context);
    }

    /**
     * updating weather data for specific widget
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     * @param city
     */
    public void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             int appWidgetId, String city) {

        this.appWidgetManager = appWidgetManager;
        this.appWidgetId = appWidgetId;
        this.city = city;
        System.out.println("updateWidget appWidgetId=" + appWidgetId);
        System.out.println("updateWidget city= " + city);

        views = new RemoteViews(context.getPackageName(), R.layout.widget_provider_layout);
        final Intent intent = new Intent(context, VaxjoWeather.class);
        intent.putExtra("city", city);
        final PendingIntent pending = PendingIntent.getActivity(context, appWidgetId, intent, 0);
        views.setOnClickPendingIntent(R.id.weather_widget, pending);

        final Intent in = new Intent(context, WeatherWidget.class);
        String action = "UPDATE_WEATHER";
        in.putExtra("city", city);
        in.putExtra("id", appWidgetId);
        in.setAction(action);
        final PendingIntent pendIn = PendingIntent.getBroadcast(context, appWidgetId, in, 0);
        views.setOnClickPendingIntent(R.id.btn_update, pendIn);
        appWidgetManager.updateAppWidget(appWidgetId, views);
        updateCity(city, context);
    }

    /**
     * downloading new weather data
     *
     * @param city
     * @param context
     */
    public void updateCity(String city, Context context) {
        System.out.println("Update City " + city);
        if (city.equalsIgnoreCase("Växjö")) {
            try {
                url = new URL("http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else if (city.equalsIgnoreCase("Stockholm")) {
            try {
                url = new URL("http://www.yr.no/place/Sweden/Stockholm/Stockholm/forecast.xml");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (city.equalsIgnoreCase("Göteborg")) {
            try {
                url = new URL("http://www.yr.no/sted/Sverige/V%C3%A4stra_G%C3%B6taland/G%C3%B6teborg/varsel.xml");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            Toast toast = Toast.makeText(context, "No data available, check the internet", Toast.LENGTH_LONG);
            toast.show();
        }
        createWeatherReport(context, url);
    }

    /**
     * create weather report
     *
     * @param context
     * @param url
     */
    private void createWeatherReport(Context context, URL url) {
        if (!checkAirPlaneMode(context)) {
            new WeatherRetriever().execute(url);
        }
    }

    /**
     * checking if airplanemode is on
     *
     * @param context
     * @return
     */
    private static boolean checkAirPlaneMode(Context context) {

        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    private void PrintReportToConsole() {
        if (this.report != null) {
                System.out.println("Wheater report loaded.");
            for (WeatherForecast forecast : report) {
                //Log.i("REPORT", forecast.toString());
            }
        } else {
            System.out.println("Weather report has not been loaded.");
        }
    }

    // Fixme "Sometimes it wants to load the url before the user inputs it"
    private class WeatherRetriever extends AsyncTask<URL, Void, WeatherReport> {
        protected WeatherReport doInBackground(URL... urls) {
            try {
                return WeatherHandler.getWeatherReport(urls[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(WeatherReport result) {
            report = result;
            if (report != null) {
                for (int i = 0; i < 1; i++) {
                    WeatherForecast forecast = report.getForecasts().get(i);
                    setViews(forecast);
                }
            } else {
            }
            PrintReportToConsole();
        }
    }

    private void setViews(WeatherForecast forecast) {

        System.out.println("Setting VIEWS");

        views.setTextViewText(R.id.date, forecast.getStartYYMMDD());
        views.setTextViewText(R.id.time, forecast.getStartHHMM() + "-" +forecast.getEndHHMM());
        views.setTextViewText(R.id.weather, forecast.getWeatherName());
        views.setTextViewText(R.id.temperature, forecast.getTemperature()+ " °C");
        views.setTextViewText(R.id.city, city);
        int imageResource;

        String s = forecast.getWeatherName();

        if (s.startsWith("Lettskyet") || s.startsWith("Delvis skyet")) {
            imageResource = R.drawable.sun_cloud;
        } else if (s.toLowerCase().contains("sol") || s.toLowerCase().contains("klarvær")|| s.toLowerCase().contains("clear sky")|| s.toLowerCase().contains(
                "sunny")) {
            imageResource = R.drawable.sunny;
        } else if (s.toLowerCase().contains("skyet")) {
            imageResource = R.drawable.cloud;
        } else if (s.toLowerCase().contains("regn")) {
            imageResource = R.drawable.rain;
        } else if (s.toLowerCase().contains("snø")) {
            imageResource = R.drawable.snow;
        } else {
            imageResource=R.drawable.sunny;
        }

        views.setImageViewResource(R.id.weather_icon2, imageResource);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
