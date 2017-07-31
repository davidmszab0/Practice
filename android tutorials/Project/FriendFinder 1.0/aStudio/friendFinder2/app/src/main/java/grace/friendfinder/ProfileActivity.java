package grace.friendfinder;

/**
 * Created by David M Szabo on 14/07/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static org.apache.commons.lang3.StringUtils.*;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.HttpUtils;

public class ProfileActivity extends Activity {

    private String TAG = "Profile";
    private Button button;
    private EditText movieGenresEditText, musicGenresEditText, nameEditText;
    private TextView musicGenresTextView, movieGenresTextView;
    private DatabaseHandler db = null;
    private ArrayList<String> moviesArray = new ArrayList<>();
    private ArrayList<String> musicArray = new ArrayList<>();
    private String userName = "";
    private String userGender = "";
    private JSONArray listMovieGenres;
    private JSONArray listMusicGenres;
    Integer user_id2 = null;
    StringEntity user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Context context = this;
        db = new DatabaseHandler(context);

        HashMap hm = db.getUserDetails();
        userName = (String) hm.get("name");
        userGender = (String) hm.get("gender");
        user_id2 =  Integer.parseInt((String) hm.get("user_id"));
        Log.d(TAG, "interest-hash: name, user_id: 1-" +
                userName +" 2-" + user_id2 );

        /* Assign listener to button */
        button = (Button)findViewById(R.id.done_button);
        button.setOnClickListener(new ButtonClick());

        movieGenresEditText = (EditText)findViewById(R.id.movie_genres_editText);
        musicGenresEditText = (EditText)findViewById(R.id.music_genres_editText);
        nameEditText = (EditText)findViewById(R.id.name_profile_editText);
        musicGenresTextView = (TextView) findViewById(R.id.music_genres_textView);
        movieGenresTextView = (TextView) findViewById(R.id.movie_genres_textView);

        moviesArray.clear();
        musicArray.clear();

        if (isNetworkAvailable() == true) {

            // TODO implement delete functions
            fillTextViews();
        } else {
            Toast.makeText(ProfileActivity.this,"The app couldn't connect to the internet. " +
                    "Please connect to the internet and " +
                    "resume the application!", Toast.LENGTH_LONG).show();
        }
    }

    private class ButtonClick implements View.OnClickListener {
        public void onClick(View v) {

            String movieGenres = movieGenresEditText.getText().toString();
            String musicGenres = musicGenresEditText.getText().toString();
            String name = nameEditText.getText().toString();
            Log.d(TAG, "user name: " + name);

            if (isBlank(name)) {
                name = userName;
            }

            if (isNetworkAvailable() == true) {
                // Fixme check if the music/movieGenres already exist in the db, then don't add interest
                //StringEntity userUpdate = userJson(userName, userGender, movieGenres, musicGenres);

                try {
                  //  System.out.println("Entity: " + IOUtils.toString(userUpdate.getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //updateUser(getApplicationContext(), userUpdate);
                postGenres(getApplicationContext(), name, movieGenres, musicGenres);
            } else {
                Toast.makeText(ProfileActivity.this,"The app couldn't connect to the internet. " +
                        "Please connect to the internet and " +
                        "resume the application!", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void fillTextViews() {
        HttpUtils.get("/user/" + user_id2, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "------- get user response: " + response);

                try {
                    user = new StringEntity(response.toString());

                    listMovieGenres = response.getJSONArray("movieGenres");
                    if(listMovieGenres != null) {
                        Log.d(TAG, "listMovieGenresObject: " + listMovieGenres);
                        for (int j = 0; j < listMovieGenres.length(); j++) {
                            JSONObject elemMovieGenres = listMovieGenres.getJSONObject(j);
                            Log.d(TAG, "elemMovieGenresObject j: " + j + " - " + elemMovieGenres);
                            if (elemMovieGenres != null) {
                                moviesArray.add(elemMovieGenres.getString("name"));
                            }
                        }
                    }

                    listMusicGenres = response.getJSONArray("musicGenres");
                    if(listMusicGenres != null) {
                        Log.d(TAG, "listMusicGenresObject: "  + listMusicGenres);
                        for(int j = 0; j < listMusicGenres.length(); j++) {
                            JSONObject elemMusicGenres = listMusicGenres.getJSONObject(j);
                            Log.d(TAG, "elemMovieGenresObject j: " + j + " - " + elemMusicGenres);
                            if(elemMusicGenres != null) {
                                musicArray.add(elemMusicGenres.getString("name"));
                            }
                        }
                    }
                    for (int i = 0; i < moviesArray.size(); i++) {
                        movieGenresTextView.append(moviesArray.get(i) + ", ");
                    }
                    for (int j = 0; j < musicArray.size(); j++) {
                        musicGenresTextView.append(musicArray.get(j) + ", ");
                    }
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

    private StringEntity userJson (String name, String gender, String movieGenres, String musicGenres) {
        JSONObject parent = new JSONObject();
        StringEntity entityUser = null;

        JSONObject movieGenresObject = new JSONObject();
        JSONObject musicGenresObject = new JSONObject();

        try {
            if (isNotBlank(movieGenres)) {
                movieGenresObject.put("name", movieGenres);
                listMovieGenres.put(movieGenresObject);
            }
            if (isNotBlank(musicGenres)) {
                musicGenresObject.put("name", musicGenres);
                listMusicGenres.put(musicGenresObject);
            }

            //parent.put("id", JSONObject.NULL);
            parent.put("name", name);
            parent.put("gender", gender);
            parent.put("movieGenres", listMovieGenres);
            parent.put("musicGenres", listMusicGenres);
            entityUser = new StringEntity(parent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityUser;
    }

    private void postGenres(final Context context, String name, String movieGenre, String musicGenre) {
        RequestParams rp = new RequestParams();
        rp.add("name", name);
        rp.add("movieGenre", movieGenre);
        rp.add("musicGenre", musicGenre);
        HttpUtils.get("/user/" + user_id2 + "/profile", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response2) {
                Log.d(TAG, "------- put user response2 : " + response2);
                try {

                    user = new StringEntity(response2.toString());

                    String name = response2.getString("name");
                    db.updateUser(name, null,
                            null, null, null);

                    // Launch Dashboard Screen
                    Intent mainActivityIntent = new Intent(context, MainActivity.class);
                    // Close all views before launching Dashboard
                    mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainActivityIntent);
                    // Close Login Screen
                    finish();
                } catch (Exception ex) {
                    ex.printStackTrace();
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                Log.d(TAG , "onFailure statusCode: "+ statusCode);
                Log.d(TAG , "onFailure headers: "+ headers);
                Log.d(TAG , "onFailure throwable: "+ throwable);
                Log.d(TAG , "onFailure object: "+ object);
            }
        });
    }

    // Fixme - I would need to remove the JSON Identity json parts when I update the user, e.g.: "users":[1] and "user":[1] to make this work
    private void updateUser (final Context context, StringEntity entityUser) {
        HttpUtils.put(context, "/user/" + user_id2, entityUser, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response2) {
                Log.d(TAG, "------- user response2 : " + response2);

                // Launch Dashboard Screen
                Intent mainActivityIntent = new Intent(context, MainActivity.class);
                // Close all views before launching Dashboard
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainActivityIntent);
                // Close Login Screen
                finish();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG , "onFailure statusCode: "+ statusCode);
                Log.d(TAG , "onFailure headers: "+ headers);
                Log.d(TAG , "onFailure responseString: "+ responseString);
                Log.d(TAG , "onFailure throwable: "+ throwable);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
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

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
