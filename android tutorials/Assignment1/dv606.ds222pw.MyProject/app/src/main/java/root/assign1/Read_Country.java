package root.assign1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by root on 2016-09-03.
 */
public class Read_Country extends Activity {

    private Button button;
    private EditText countryEditText, yearEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_country);

        /* Assign listener to button */
        button = (Button)findViewById(R.id.done_button);
        button.setOnClickListener(new ButtonClick());

        countryEditText = (EditText)findViewById(R.id.country_reader);
        yearEditText = (EditText)findViewById(R.id.year_reader);
    }


    private class ButtonClick implements View.OnClickListener {
        public void onClick(View v) {

            String country = countryEditText.getText().toString();
            String year = yearEditText.getText().toString();

    		//Create new result intent
            Intent reply = new Intent();
            reply.putExtra("result1", country);
            reply.putExtra("result2", year);
            setResult(RESULT_OK,reply);
            finish();   // Close this activity

        }
    }
}