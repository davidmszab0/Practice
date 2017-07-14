package grace.friendfinder;

/**
 * Created by grace on 14/07/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InterestActivity extends Activity {

    private Button button;
    private EditText movieGenresEditText, musicGenresEditText;
    private TextView musicGenresTextView, movieGenresTextView;

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

        // TODO fill the textViews with data
        fillTextViews();

    }

    private class ButtonClick implements View.OnClickListener {
        public void onClick(View v) {

            String movieGenres = movieGenresEditText.getText().toString();
            String musicGenres = musicGenresEditText.getText().toString();


        }
    }
    private void fillTextViews() {

    }
}
