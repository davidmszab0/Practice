package root.assign2.myCountries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import root.assign2.R;

import static java.security.AccessController.getContext;

/**
 * Created by grace on 2016-09-19.
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

            boolean ok = ValidateInputString(country);
            ok = ok && tryParseInt(year);

            if (ok) {
                //Create new result intent
                Intent reply = new Intent();
                reply.putExtra("countryResult1", country);
                reply.putExtra("yearResult1", Integer.parseInt(year));
                setResult(RESULT_OK, reply);
                finish();   // Close this activity
            }
        }
    }

    private boolean ValidateInputString (String text1) {
        boolean ok = false;
        if (text1 != null && !text1.isEmpty()) {
            ok = true;
            return ok;
        }
        Toast.makeText(getApplicationContext(), "country field was empty, please try again",
                Toast.LENGTH_LONG).show();
        return ok;
    }
    private boolean tryParseInt(String text) {
        boolean ok = false;
        if (text != null && !text.isEmpty()) {
            if (text.trim().matches("[0-9]+")) {
                // return Integer.valueOf(text.trim());
                ok = true;
                return ok;
            }
        } else {
            Toast.makeText(getApplicationContext(), "year field wasn't an integer or was empty, please try again",
                    Toast.LENGTH_LONG).show();
        }
        return ok;
    }
}