package grace.assignment3.WeatherWidget;

import android.app.Activity;
import android.os.Bundle;

import grace.assignment3.R;

/**
 * Widget Activity, only loads layout
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is not my, David Szabo's work!
 */
public class WidgetActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_provider_layout);
	}
}
