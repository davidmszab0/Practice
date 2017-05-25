package grace.assignment3.WeatherWidget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import grace.assignment3.R;
import grace.assignment3.WeatherApp.VaxjoWeather;

/**
 * Activity that let's user select city to get weather information, then start widget
 * is placed on screen
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is not my, David Szabo's work!
 */
public class PickCity extends Activity {

	private final String[] cities = { "Växjö", "Stockholm", "Göteborg" };
	private ListView lv;
	private ArrayAdapter<String> adapt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_pick_city);

		lv = (ListView) findViewById(R.id.citylist);
		adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
		lv.setAdapter(adapt);

		lv.setOnItemClickListener(new OnItemClick());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.widget_pick_city, menu);
		return true;
	}

	// starts intent with selected city
	public class OnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			String city = (String) lv.getItemAtPosition(arg2);
			Intent intent = new Intent(PickCity.this, VaxjoWeather.class);
			intent.putExtra("city", city);

			startActivity(intent);
		}

	}
}
