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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.HttpUtils;

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
            moviesArray.add("K-pax");
            musicArray.add("Hillsong");

            fillArrays();

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
            //moviesLine.setText(movies.get(position));
            //musicLine.setText(music.get(position));

            return rowView;
        }
    }

    private void fillArrays() {
        HttpUtils.get("/user/all", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "------- this is the user response: " + response);

                try {

                    for (int i = 0; i < response.length(); i++){
                        JSONObject userObject = response.getJSONObject(i);
                        Log.d(TAG, "jsonObject: " + userObject);
                        String name = userObject.getString("name");
                        String gender = userObject.getString("gender");
                        namesArray.add(name);
                        gendersArray.add(gender);
                        //moviesArray.add("K-pax");
                        //musicArray.add("Hillsong");
                    }
                    friendsAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG , "onFailure statusCode: "+ statusCode);
                Log.d(TAG , "onFailure headers: "+ headers);
                Log.d(TAG , "onFailure responseString: "+ responseString);
                Log.d(TAG , "onFailure throwable: "+ throwable);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray object) {
                Log.d(TAG , "onFailure statusCode: "+ statusCode);
                Log.d(TAG , "onFailure headers: "+ headers);
                Log.d(TAG , "onFailure throwable: "+ throwable);
                Log.d(TAG , "onFailure object: "+ object);
            }
        });
    }
}
