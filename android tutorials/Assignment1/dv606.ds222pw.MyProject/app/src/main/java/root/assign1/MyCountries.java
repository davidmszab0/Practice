package root.assign1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by root on 2016-09-03.
 */
public class MyCountries extends Activity {

    // look at http://www.journaldev.com/9438/android-sqlite-database-example-tutorial

    private ListView listView;
    private MyAdapter adapter;
    private Activity main_activity;
    String year="";
    String country="";
    String data_input;
    ArrayList<String> info = new ArrayList<String>();
    ArrayList<String> text_lines = new ArrayList<String>();
    public static final String PREFS_COUNT = "MyPrefsFile";
    String[] mobileArray;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_bar);


        listView = (ListView)findViewById(R.id.listView);
        adapter = new MyAdapter(this,R.layout.list_item);

        main_activity = this;  // Simplifies Intent handling
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // An XML based menu specification.
        // See res/menu/action_menu.xml for details
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_menu_record:


                /* Start new Activity that returns a result */
                Intent intent = new Intent(this,Read_Country.class);
                //main_activity.startActivityForResult(intent,0);
                main_activity.startActivityForResult(intent,0);
                return true;

            case android.R.id.home:  // Predefined icon ID

                // app icon in action bar clicked ==>  go home
                Intent intent2 = new Intent(this, MainList.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        /*
         * Adapter to populate list with colors and integers
         */
    class MyAdapter extends ArrayAdapter<Object> {

        public MyAdapter(Context context, int textViewResourceId) {
            super(context,textViewResourceId);
        }

        @Override   // Called when updating the ListView
        public View getView(int position, View convertView, ViewGroup parent) {
            /* Reuse super handling ==> A TextView from R.layout.list_item */
            TextView tv = (TextView) super.getView(position,convertView,parent);

                /* Find corresponding entry */
                Object obj = getItem(position);

            /* Update TextView with string information */
                tv.setText(obj.toString());
                tv.setBackgroundColor(0xffffffff);
                tv.setTextColor(0xff000000);  // Black

            return tv;
        }
    }

    /** Called when the activity receives a results. */
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            country = result.getStringExtra("result1");
            year = result.getStringExtra("result2");
            data_input = year + " " + country;

            info.add(data_input);

            for (int i = 0; i < info.size(); i++) {
                System.out.println(info.get(i));
            }
            adapter.add(data_input);  // Add new string to data list
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }
}
