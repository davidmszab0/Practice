package grace.friendfinder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
import grace.friendfinder.domain.User;
import grace.friendfinder.preferences.SimplePreferenceActivity;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.FriendsAdapter;
import grace.friendfinder.utils.HttpUtils;
import grace.friendfinder.utils.SharedPreference;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * @author David M Szabo on 05/06/2017.
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // TODO clean up the @strings

    private String TAG = "Main";
    private DatabaseHandler db = null;
    // TODO create a UserManager class to manage the array of users
    private FriendsAdapter friendsAdapter;
    private SearchManager searchManager;
    private MenuItem searchMenuItem;
    private User user;
    private ArrayList<User> userArray = new ArrayList<>();
    ListView listView;

    // search queries will be saved to shared Prefs
    private SharedPreference sharedPreference;
    private String searchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);

        db = new DatabaseHandler(getApplicationContext());

        // if the number of rows in the SQLite db is less than 1, then there is no user added to the db
        if (db.getRowCount()) {
            setContentView(R.layout.list_view);
            Log.d(TAG, "on the mainActivity screen");

            if (isNetworkAvailable() == true) {
                userArray.clear(); // the array is loaded in the fillArrays every time the mainActivity is loaded
                fillArrays(); // gets all the users
            } else {
                Toast.makeText(MainActivity.this,"The app couldn't connect to the internet. " +
                        "Please connect to the internet and " +
                        "resume the application!", Toast.LENGTH_LONG).show();
            }

            // sharedPref is used for searchQuery, which is used on onResume()
            sharedPreference = new SharedPreference();

            listView = (ListView) findViewById(R.id.listView);
            friendsAdapter = new FriendsAdapter(this, userArray);
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

        // putting the search service to the toolbar widget
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
                startActivity(new Intent(this, SimplePreferenceActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // for the SearchView query intents
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
        sharedPreference.save(this, queryStr);
        // to get it: searchQuery = sharedPreference.getValue(context);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String queryStr) {
        // using the baseAdapter that implements the filtering
        friendsAdapter.getFilter().filter(queryStr);
        sharedPreference.save(this, queryStr);
        return false;
    }

    // set the Background color from Settings
    // set the latest search Query
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String value = sharedPref.getString("list_color_preference", "1");
        Log.d(TAG, "preferences: " + value);
        switch (value) {
            case "1":
                listView.setBackgroundColor(ContextCompat.getColor(listView.getContext(), R.color.colorGrey));
                break;
            case "2":
                listView.setBackgroundColor(ContextCompat.getColor(listView.getContext(), R.color.colorPrimary));
                break;
            case "3":
                listView.setBackgroundColor(ContextCompat.getColor(listView.getContext(), R.color.colorAccent));
                break;
            default:
                listView.setBackgroundColor(ContextCompat.getColor(listView.getContext(), R.color.colorWhite));
        }

        searchQuery = sharedPreference.getValue(this);
        if (isNotBlank(searchQuery)) {
            // get the latest search results
            Log.d(TAG, "queryStr2: " + searchQuery);
            friendsAdapter.getFilter().filter(searchQuery);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * To get all the users.
     * It also fills the User Array
     */
    private void fillArrays() {

        // REST call to get all users
        HttpUtils.get("/user/all", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d(TAG, "------- this is the user response: " + response);

                try {

                    // Parsing the REST response
                    for (int i = 0; i < response.length(); i++){
                        JSONObject userObject = response.getJSONObject(i);
                        Log.d(TAG, "userObject: " + i + ": " + userObject);
                        String name = userObject.getString("name");
                        String gender = userObject.getString("gender");
                        user = new User();
                        user.setName(name);
                        user.setGender(gender);

                        JSONArray listMovieGenres = userObject.getJSONArray("movieGenres");
                        if(listMovieGenres != null){
                            //Log.d(TAG, "listMovieGenresObject: "  + listMovieGenres);
                            for(int j = 0; j < listMovieGenres.length(); j++){
                                JSONObject elemMovieGenres = listMovieGenres.getJSONObject(j);
                               // Log.d(TAG, "elemMovieGenresObject i: " + i +",j: " + j + " - " + elemMovieGenres);
                                if(elemMovieGenres != null){
                                    user.addMovieGenresArray(elemMovieGenres.getString("name"));
                                }
                            }
                        }

                        JSONArray listMusicGenres = userObject.getJSONArray("musicGenres");
                        if(listMusicGenres != null){
                            //Log.d(TAG, "listMusicGenresObject: "  + listMusicGenres);
                            for(int j = 0; j < listMusicGenres.length(); j++){
                                JSONObject elemMusicGenres = listMusicGenres.getJSONObject(j);
                                //Log.d(TAG, "elemMovieGenresObject i: " + i +",j: " + j + " - " + elemMusicGenres);
                                if(elemMusicGenres != null){
                                    user.addMusicGenresArray(elemMusicGenres.getString("name"));
                                }
                            }
                        }
                        userArray.add(user);
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
