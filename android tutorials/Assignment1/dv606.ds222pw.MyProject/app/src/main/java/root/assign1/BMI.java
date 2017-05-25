package root.assign1;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMI extends Activity {

    private AlertDialog alert;
    private TextView display;
    private Button compute, reset;
    private EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        /* Find text display */
        display =  (TextView)findViewById(R.id.display);
        System.out.println("1: " + display);

		/* Setup text field view */
        editText1 = (EditText) findViewById(R.id.editText1);
        System.out.println("2: "+editText1);

        editText2 = (EditText) findViewById(R.id.editText2);
        System.out.println("2: "+editText2);

		/* Assign listeners to the buttons */
        compute = (Button)findViewById(R.id.compute_button);
        compute.setOnClickListener(new Click());
        reset = (Button)findViewById(R.id.reset_button);
        reset.setOnClickListener(new Click());

		/* Prepare alert message raised if non-integer received */
        alert = setup_alert();
    }

    private class Click implements View.OnClickListener {
        public void onClick(View v) {
            if (v == compute) {

                try {
                    // computing the BMI index
                    String length = editText1.getText().toString().trim();
                    String weight = editText2.getText().toString().trim();

                    double n1 = Double.parseDouble(length);
                    double n2 = Double.parseDouble(weight);
                    n1=n1/100;

                    double BMI = n2 / (n1*n1);
                    display.setText("Your BMI index is: " + BMI);

                } catch (NumberFormatException nfe) {
                    alert.show();   // Show error alerts
                }
            }
            else if (v == reset){
                //resetting the edittext fields and the textview
                editText1.setText("");
                editText2.setText("");
                display.setText("give a new value!");
            }
        }
    }

    /* AlertDialog Setup and Handling from the first lecture slides */
    private AlertDialog setup_alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_msg);
        builder.setCancelable(false);
        builder.setTitle(R.string.alert_title);
        builder.setPositiveButton("Done", new DialogDone());
        return builder.create();
    }

    //from the first lecture slides
    private class DialogDone implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            dialog.dismiss();
        }
    }
}

