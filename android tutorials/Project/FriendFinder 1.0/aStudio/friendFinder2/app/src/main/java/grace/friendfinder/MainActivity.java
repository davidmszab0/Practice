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

import java.lang.reflect.Array;
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
    private ArrayList<ArrayList<String>> moviesArray = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> musicArray = new ArrayList<ArrayList<String>>();
    private FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);

        db = new DatabaseHandler(getApplicationContext());

        if (db.getRowCount()) {
            setContentView(R.layout.list_view);
            Log.d(TAG, "on the mainActivity screen");

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
        private ArrayList<ArrayList<String>> movies;
        private ArrayList<ArrayList<String>> music;

        public FriendsAdapter(Context context, int textViewResourceId, ArrayList<String> name, ArrayList<String> gender,
                              ArrayList<ArrayList<String>> movies, ArrayList<ArrayList<String>> music) {
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
            Log.d("getView ", String.valueOf(position));

            TextView nameLine = (TextView) rowView.findViewById(R.id.nameId);
            TextView genderLine = (TextView) rowView.findViewById(R.id.genderId);
            TextView moviesLine = (TextView) rowView.findViewById(R.id.moviesId);
            TextView musicLine = (TextView) rowView.findViewById(R.id.musicId);

            nameLine.setText("Name: " + name.get(position));
            genderLine.setText("Gender: " + gender.get(position));
            for (int i = 0; i < movies.get(position).size(); i++) {
                moviesLine.append(movies.get(position).get(i) + ", ");
            }
            for (int i = 0; i < music.get(position).size(); i++) {
                musicLine.append(music.get(position).get(i) + ", ");
            }

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
                        Log.d(TAG, "userObject: " + i + ": " + userObject);
                        String name = userObject.getString("name");
                        String gender = userObject.getString("gender");

                        JSONArray listMovieGenres = userObject.getJSONArray("movieGenres");
                        if(listMovieGenres != null){
                            Log.d(TAG, "listMovieGenresObject: "  + listMovieGenres);
                            for(int j = 0; j < listMovieGenres.length(); j++){
                                JSONObject elemMovieGenres = listMovieGenres.getJSONObject(j);
                                Log.d(TAG, "elemMovieGenresObject i: " + i +",j: " + j + " - " + elemMovieGenres);
                                if(elemMovieGenres != null){
                                    moviesArray.add(new ArrayList<String>());
                                    moviesArray.get(i).add(elemMovieGenres.getString("name"));
                                }
                            }
                        }

                        JSONArray listMusicGenres = userObject.getJSONArray("musicGenres");
                        if(listMusicGenres != null){
                            Log.d(TAG, "listMusicGenresObject: "  + listMusicGenres);
                            for(int j = 0; j < listMusicGenres.length(); j++){
                                JSONObject elemMusicGenres = listMusicGenres.getJSONObject(j);
                                Log.d(TAG, "elemMovieGenresObject i: " + i +",j: " + j + " - " + elemMusicGenres);
                                if(elemMusicGenres != null){
                                    musicArray.add(new ArrayList<String>());
                                    musicArray.get(i).add(elemMusicGenres.getString("name"));
                                }
                            }
                        }
                        namesArray.add(name);
                        gendersArray.add(gender);
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
