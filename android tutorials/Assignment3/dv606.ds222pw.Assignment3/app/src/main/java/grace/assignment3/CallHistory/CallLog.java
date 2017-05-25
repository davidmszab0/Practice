package grace.assignment3.CallHistory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import grace.assignment3.R;

/**
 * Class to show list of incoming calls
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is not my, David Szabo's work!
 *
 * Android Studio -> extended controls/phone and there you can send msg and call
 */

public class CallLog extends AppCompatActivity {

	private ListView listView;
	private static callAdapter callAdapter;
	private static DbOperations dbOperations;
	private static List<CallInfo> callInfos;

	/**
	 * Initialising view, database and other required fields
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		setContentView(R.layout.call_history_call_log);

		dbOperations = new DbOperations(this);
		dbOperations.open();
		callInfos = dbOperations.getAllCalls();

		listView = (ListView) findViewById(android.R.id.list);
		callAdapter = new callAdapter(this, R.layout.call_history_list_row_calls, callInfos);
		TextView empty = (TextView) findViewById(R.id.empty);
		listView.setEmptyView(empty);
		listView.setAdapter(callAdapter);
		registerForContextMenu(listView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call_history_call_log, menu);
		return true;
	}

	/**
	 * Create context menu to let user delete, call or send numbers via message
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == android.R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("" + callInfos.get(info.position).getNumber() + " "
					+ callInfos.get(info.position).getDate().toString());
			menu.add(0, 0, 0, getResources().getString(R.string.delete));
			menu.add(1, 1, 1, getResources().getString(R.string.send_message));
			menu.add(2, 2, 2, getResources().getString(R.string.callInfo));
		}
	}

	/**
	 * Handling context-item selected calls
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case 0: // delete item
			CallInfo callInfo = callAdapter.getItem(info.position);
			dbOperations.open();
			dbOperations.deleteCall(callInfo);
			dbOperations.close();
			callAdapter.remove(callInfo);
			break;
		case 1: // send message
			String number = callInfos.get(info.position).getNumber();
			Intent sendIntent = new Intent(Intent.ACTION_SEND);
			sendIntent.setType("text/plain");
			sendIntent.putExtra(Intent.EXTRA_TEXT, number);
			try {
				startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.send_via)));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(CallLog.this, getResources().getString(R.string.no_messaging_app),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case 2: // call number
			String callNumber = "tel:" + callInfos.get(info.position).getNumber().trim();
			Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(callNumber));
			startActivity(callIntent);
			break;
		default:
			return super.onContextItemSelected(item);
		}

		return true;
	}

	/**
	 * Adapter Class for showing Call objects in ListView
	 */
	public class callAdapter extends ArrayAdapter<CallInfo> {

		public callAdapter(Context context, int resource, List<CallInfo> objects) {
			super(context, resource, objects);
		}

		@Override
		// Called when updating the ListView
		public View getView(int position, View convertView, ViewGroup parent) {
			View row;
			if (convertView == null) {
				row = getLayoutInflater().inflate(R.layout.call_history_list_row_calls, parent, false);
			} else {
				row = convertView;
			}

			TextView viewDate = (TextView) row.findViewById(R.id.show_date);
			TextView viewNumber = (TextView) row.findViewById(R.id.show_number);
			viewDate.setText(callInfos.get(position).getDate());
			viewNumber.setText(callInfos.get(position).getNumber());
			return row;
		}
	}

	/**
	 * method for updating ListView with specific Call
	 */
	public static void updateListView() {
		List<CallInfo> callInfoList = dbOperations.getAllCalls();
		callAdapter.clear();
		callAdapter.addAll(callInfoList);
		callAdapter.notifyDataSetChanged();
	}
}
