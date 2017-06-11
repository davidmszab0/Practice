package grace.friendfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import grace.friendfinder.MainActivity;
import grace.friendfinder.R;

/**
 * @author David M Szabo
 */
public class LoginActivity extends Activity {

    Button btnLogin;
    Button btnRegistering;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    TextView textViewForgotPassword;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistering = (Button) findViewById(R.id.btnRegister);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        //textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Context context = getApplicationContext();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // check for login response
                try {
                        // Store user details in MySql Database
                        DatabaseHandler db = new DatabaseHandler();
                        if (db.checkUser(email, password))
                            loginErrorMsg.setText("Correct password and user name");

                        // Launch Dashboard Screen
    /*                            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);

                        // Close all views before launching Dashboard
                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainActivity);

                        // Close Login Screen
                        finish();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Link to Register Screen
        btnRegistering.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

        /*textViewForgotPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });*/
    }


}