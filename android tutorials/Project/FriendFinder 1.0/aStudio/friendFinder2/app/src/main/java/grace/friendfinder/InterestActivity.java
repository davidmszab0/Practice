package grace.friendfinder;

/**
 * Created by grace on 14/07/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import cz.msebera.android.httpclient.Header;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.HttpUtils;

public class InterestActivity extends Activity {

    private String TAG = "Interest";
    private Button button;
    private EditText movieGenresEditText, musicGenresEditText;
    private TextView musicGenresTextView, movieGenresTextView;
    private DatabaseHandler db = null;
    private ArrayList<ArrayList<String>> moviesArray = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> musicArray = new ArrayList<ArrayList<String>>();
    private String userName;
    private String userGender;

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

        fillTextViews();

    }

    private class ButtonClick implements View.OnClickListener {
        public void onClick(View v) {

            String movieGenres = movieGenresEditText.getText().toString();
            String musicGenres = musicGenresEditText.getText().toString();

            // TODO: add to the movieGenres and musicGenres and save them on the User Mysql db

        }
    }
    private void fillTextViews() {
        db = new DatabaseHandler(getApplicationContext());
        HashMap hm = db.getUserDetails();
        String name2 = (String) hm.get("name");
        Integer user_id2 =  Integer.parseInt((String) hm.get("user_id"));
        Log.d(TAG, "interest-hash: name, user_id: 1-" +
                name2 +" 2-" + user_id2 );

        HttpUtils.get("/user/" + user_id2, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, "------- user response: " + response);

                try {
                    moviesArray.add(new ArrayList<String>());

                    JSONArray listMovieGenres = response.getJSONArray("movieGenres");
                        if(listMovieGenres != null) {
                            Log.d(TAG, "listMovieGenresObject: " + listMovieGenres);
                            for (int j = 0; j < listMovieGenres.length(); j++) {
                                JSONObject elemMovieGenres = listMovieGenres.getJSONObject(j);
                                Log.d(TAG, "elemMovieGenresObject j: " + j + " - " + elemMovieGenres);
                                if (elemMovieGenres != null) {
                                    moviesArray.get(0).add(elemMovieGenres.getString("name"));
                                }
                            }
                        }
                    musicArray.add(new ArrayList<String>());

                    JSONArray listMusicGenres = response.getJSONArray("musicGenres");
                        if(listMusicGenres != null) {
                            Log.d(TAG, "listMusicGenresObject: "  + listMusicGenres);
                            for(int j = 0; j < listMusicGenres.length(); j++) {
                                JSONObject elemMusicGenres = listMusicGenres.getJSONObject(j);
                                Log.d(TAG, "elemMovieGenresObject j: " + j + " - " + elemMusicGenres);
                                if(elemMusicGenres != null) {
                                    musicArray.get(0).add(elemMusicGenres.getString("name"));
                                }
                            }
                        }
                    for (int i = 0; i < moviesArray.get(0).size(); i++) {
                        movieGenresTextView.append(moviesArray.get(0).get(i) + ", ");
                    }
                    for (int j = 0; j < musicArray.get(0).size(); j++) {
                        musicGenresTextView.append(musicArray.get(0).get(j) + ", ");
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
}
