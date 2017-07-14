package grace.friendfinder;

/**
 * Created by grace on 14/07/17.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.HttpUtils;

public class InterestActivity extends Activity {

    private String TAG = "Interest";
    private Button button;
    private EditText movieGenresEditText, musicGenresEditText;
    private TextView musicGenresTextView, movieGenresTextView;
    private DatabaseHandler db = null;
    private ArrayList<String> moviesArray = new ArrayList<>();
    private ArrayList<String> musicArray = new ArrayList<String>();
    private String userName = "";
    private String userGender = "";
    private JSONArray listMovieGenres;
    private JSONArray listMusicGenres;
    Integer user_id2 = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        /* Assign listener to button */
        button = (Button)findViewById(R.id.done_button);
        button.setOnClickListener(new ButtonClick());

        movieGenresEditText = (EditText)findViewById(R.id.movie_genres_editText);
        musicGenresEditText = (EditText)findViewById(R.id.music_genres_editText);
        musicGenresTextView = (TextView) findViewById(R.id.music_genres_textView);
        movieGenresTextView = (TextView) findViewById(R.id.movie_genres_textView);

        if (isNetworkAvailable() == true) {
            fillTextViews();
        } else {
            Toast.makeText(InterestActivity.this,"The app couldn't connect to the internet. " +
                    "Please connect to the internet and " +
                    "resume the application!", Toast.LENGTH_LONG).show();
        }
    }

    private class ButtonClick implements View.OnClickListener {
        public void onClick(View v) {

            String movieGenres = movieGenresEditText.getText().toString();
            String musicGenres = musicGenresEditText.getText().toString();

            if (isNetworkAvailable() == true) {
                // Fixme check if the interest already exists
                StringEntity userUpdate = userJson(userName, userGender, movieGenres, musicGenres);
                updateUser(getApplicationContext(), userUpdate);
            } else {
                Toast.makeText(InterestActivity.this,"The app couldn't connect to the internet. " +
                        "Please connect to the internet and " +
                        "resume the application!", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void fillTextViews() {
        db = new DatabaseHandler(getApplicationContext());
        HashMap hm = db.getUserDetails();
        String name2 = (String) hm.get("name");
        user_id2 =  Integer.parseInt((String) hm.get("user_id"));
        Log.d(TAG, "interest-hash: name, user_id: 1-" +
                name2 +" 2-" + user_id2 );

        HttpUtils.get("/user/" + user_id2, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "------- user response: " + response);

                try {
                    userName = response.getString("name");
                    userGender = response.getString("gender");

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

        JSONObject movie = new JSONObject();
        JSONObject music = new JSONObject();

        try {
            movie.put("name", movieGenres);
            listMovieGenres.put(movie);
            music.put("name", musicGenres);
            listMusicGenres.put(music);

            parent.put("name", name);
            parent.put("gender", gender);
            parent.put("movieGenres", listMovieGenres);
            parent.put("musicGenres", listMusicGenres);
            entityUser = new StringEntity(parent.toString());
            Log.d(TAG, "entityAccount: " + entityUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityUser;
    }

    private void updateUser (Context context, StringEntity entityUser) {
        HttpUtils.put(context, "/user/" + user_id2, entityUser, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response2) {
                Log.d(TAG, "------- user response2 : " + response2);

                // TODO launch login Activity
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
}
