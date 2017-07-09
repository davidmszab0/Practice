package grace.friendfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.HTTpTask;
import grace.friendfinder.utils.HttpUtils;

/**
 * @author David M Szabo
 */
public class LoginActivity extends Activity {

    Button btnLogin;
    Button btnRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    TextView textViewForgotPassword;

    private String TAG = "Login";

    String url = "http://10.0.2.2:8080/account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegisterLogin);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        //textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // check for login response
                try {
                    RequestParams rp = new RequestParams();
                    rp.add("email", "david@szabo.com");
                    rp.add("password", "empty");

                    HttpUtils.get("/account", rp, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            // If the response is JSONObject instead of expected JSONArray
                            Log.d(TAG, "------- this is response : " + response);
                            try {
                                JSONObject serverResp = new JSONObject(response.toString());
                                String id = serverResp.getString("accountId");
                                String email = serverResp.getString("email");
                                String password = serverResp.getString("password");
                                String created_at = serverResp.getString("createdAt");
                                Log.d(TAG, "the id, email, password is: " + id + " " +email + " " + password + " " + created_at);

                                // storing the user in the local SQLite db
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                db.resetTables(); // first deleting all the rows
                                db.addUser(email, created_at);
                                Log.d(TAG, "Created user in the local SQLite db.");

                                if (db.getRowCount()) {
                                    HashMap hm = db.getUserDetails();
                                    String email2 = (String) hm.get("email");
                                    Log.d(TAG, "email_login: " + email2);
                                }

                                // Launch Dashboard Screen
                                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                                // Close all views before launching Dashboard
                                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainActivityIntent);
                                // Close Login Screen
                                finish();

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
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Link to Register Screen
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }
}