package grace.friendfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import grace.friendfinder.utils.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Main";
    private DatabaseHandler db = null;
    private ArrayList<String> namesArray = new ArrayList<>();
    private ArrayList<String> gendersArray = new ArrayList<>();
    private ArrayList<String> moviesArray = new ArrayList<>();
    private ArrayList<String> musicArray = new ArrayList<>();
    private FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);

        db = new DatabaseHandler(getApplicationContext());

        if (db.getRowCount()) {
            setContentView(R.layout.list_view);
            Log.d(TAG, "on the mainActivity screen");

            // TODO fill the arrays with data
            namesArray.add("David");
            gendersArray.add("Male");
            moviesArray.add("K-pax");
            musicArray.add("Hillsong");

            ListView listView = (ListView) findViewById(R.id.listView);
            friendsAdapter = new FriendsAdapter(this, R.layout.list_item,
                    namesArray, gendersArray, moviesArray, musicArray);
            listView.setAdapter(friendsAdapter);

        } else {
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing main activity screen
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // An XML based main_menu specification.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:  // Predefined icon ID
                db.resetTables();
                Log.d(TAG, "Deleted the user from the SQLite local db.");

                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();
                return true;
            case R.id.settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ------------ ARRAY ADAPTER ---------------
    public class FriendsAdapter extends ArrayAdapter<String> {
        private Context context;
        private ArrayList<String> name;
        private ArrayList<String> gender;
        private ArrayList<String> movies;
        private ArrayList<String> music;

        public FriendsAdapter(Context context, int textViewResourceId, ArrayList<String> name, ArrayList<String> gender,
                              ArrayList<String> movies, ArrayList<String> music) {
            super(context, textViewResourceId, name);
            this.context = context;
            this.name = name;
            this.gender = gender;
            this.movies = movies;
            this.music = music;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_item, parent, false);

            TextView nameLine = (TextView) rowView.findViewById(R.id.nameId);
            TextView genderLine = (TextView) rowView.findViewById(R.id.genderId);
            TextView moviesLine = (TextView) rowView.findViewById(R.id.moviesId);
            TextView musicLine = (TextView) rowView.findViewById(R.id.musicId);

            nameLine.setText(name.get(position));
            genderLine.setText(gender.get(position));
            moviesLine.setText(movies.get(position));
            musicLine.setText(music.get(position));

            return rowView;
        }
    }
}
