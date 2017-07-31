package grace.friendfinder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.HttpUtils;

/**
 * @author David M Szabo.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String TAG = "Main";
    private DatabaseHandler db = null;
    private ArrayList<String> namesArray = new ArrayList<>();
    private ArrayList<String> gendersArray = new ArrayList<>();
    private ArrayList<ArrayList<String>> moviesArray = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> musicArray = new ArrayList<ArrayList<String>>();
    private FriendsAdapter friendsAdapter;
    private SearchManager searchManager;
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);

        db = new DatabaseHandler(getApplicationContext());

        if (db.getRowCount()) {
            setContentView(R.layout.list_view);
            Log.d(TAG, "on the mainActivity screen");

            if (isNetworkAvailable() == true) {
                fillArrays();
            } else {
                Toast.makeText(MainActivity.this,"The app couldn't connect to the internet. " +
                        "Please connect to the internet and " +
                        "resume the application!", Toast.LENGTH_LONG).show();
            }
            handleIntent(getIntent());

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

        searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

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
            case R.id.add_interest:
                Log.d(TAG, "Update Profile");

                Intent interestIntent = new Intent(this, ProfileActivity.class);
                interestIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(interestIntent);
                finish();
                return true;
            case R.id.settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to activity_search your data somehow
            doSearch(query);
        }
    }

    private void doSearch(String queryStr) {
        Log.d(TAG, "Your search: " + queryStr);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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

            TextView nameLine = (TextView) rowView.findViewById(R.id.nameId);
            TextView genderLine = (TextView) rowView.findViewById(R.id.genderId);
            TextView moviesLine = (TextView) rowView.findViewById(R.id.moviesGenresId);
            TextView musicLine = (TextView) rowView.findViewById(R.id.musicGenresId);

            nameLine.setText("Name: " + name.get(position));
            genderLine.setText("Gender: " + gender.get(position));
            for (int i = 0; i < movies.get(position).size(); i++) {
                moviesLine.append(movies.get(position).get(i) + ", ");
            }
            for (int j = 0; j < music.get(position).size(); j++) {
                musicLine.append(music.get(position).get(j) + ", ");
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
                        moviesArray.add(new ArrayList<String>());

                        JSONArray listMovieGenres = userObject.getJSONArray("movieGenres");
                        if(listMovieGenres != null){
                            //Log.d(TAG, "listMovieGenresObject: "  + listMovieGenres);
                            for(int j = 0; j < listMovieGenres.length(); j++){
                                JSONObject elemMovieGenres = listMovieGenres.getJSONObject(j);
                               // Log.d(TAG, "elemMovieGenresObject i: " + i +",j: " + j + " - " + elemMovieGenres);
                                if(elemMovieGenres != null){
                                    moviesArray.get(i).add(elemMovieGenres.getString("name"));
                                }
                            }
                        }
                        musicArray.add(new ArrayList<String>());

                        JSONArray listMusicGenres = userObject.getJSONArray("musicGenres");
                        if(listMusicGenres != null){
                            //Log.d(TAG, "listMusicGenresObject: "  + listMusicGenres);
                            for(int j = 0; j < listMusicGenres.length(); j++){
                                JSONObject elemMusicGenres = listMusicGenres.getJSONObject(j);
                                //Log.d(TAG, "elemMovieGenresObject i: " + i +",j: " + j + " - " + elemMusicGenres);
                                if(elemMusicGenres != null){
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
