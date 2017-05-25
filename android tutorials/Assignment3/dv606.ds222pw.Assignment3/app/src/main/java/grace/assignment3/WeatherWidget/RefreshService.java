package grace.assignment3.WeatherWidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import grace.assignment3.R;

/**
 * Created by grace on 23/03/17.
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is not my, David Szabo's work!
 */

public class RefreshService extends Service {

    private int appWidgetId;
    private String city;
    private RemoteViews remoteViews;
    private AppWidgetManager appWidgetManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // called when starting the service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWidgetInstance(intent);
        return START_STICKY; // service ends only when we tell it to
    }

    // responsible for updating weather data for specific widget-instance
    private void updateWidgetInstance(Intent intent) {
        int[] widgetIds = intent.getIntArrayExtra("appWidgetIds");
        Context context = getBaseContext();

        // widgetId is 0 and then 1
        final int N = widgetIds.length;
        for (int i = 0; i < N; i++) {
            this.appWidgetId = widgetIds[i];
            if (WeatherWidgetConfiguration.loadBoolPref(context, appWidgetId) == true) {
                // Fixme "sometimes it takes a few times to get the city"
                city = WeatherWidgetConfiguration.loadPrefs(context, appWidgetId);
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider_layout);

                appWidgetManager = AppWidgetManager.getInstance(context);
                WeatherWidget weatherWidget = new WeatherWidget();
                weatherWidget.updateWidget(context, appWidgetManager, appWidgetId, city);
                weatherWidget.updateCity(city, context);
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            }
        }
        stopSelf(); // service no longer needed
    }
}
