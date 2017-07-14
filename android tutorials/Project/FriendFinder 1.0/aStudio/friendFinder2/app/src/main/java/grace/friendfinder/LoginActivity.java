package grace.friendfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private DatabaseHandler db = null;

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
                final Context context = getApplicationContext();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // check for login response
                try {
                    RequestParams rp = new RequestParams();
                    rp.add("email", email);
                    rp.add("password", password);

                    HttpUtils.get("/account", rp, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            // If the response is JSONObject instead of expected JSONArray
                            Log.d(TAG, "------- this is the account response: " + response);
                            try {
                                JSONObject serverResp = new JSONObject(response.toString());
                                String email = serverResp.getString("email");
                                String created_at = serverResp.getString("createdAt");

                                // storing the user in the local SQLite db
                                db = new DatabaseHandler(getApplicationContext());
                                db.resetTables(); // first deleting all the rows
                                db.addUser("", "", email, created_at);
                                Log.d(TAG, "Created user in the local SQLite db.");

                                if (db.getRowCount()) {
                                    HashMap hm = db.getUserDetails();
                                    String name2 = (String) hm.get("name");
                                    String gender2 = (String) hm.get("gender");
                                    String email2 = (String) hm.get("email");
                                    String created_at2 = (String) hm.get("created_at");
                                    Log.d(TAG, "login-hash: name, gender, email, created_at: 1-" +
                                            name2 +" 2-"+gender2+" 3-"+email2+" 4-"+created_at2);
                                }

                                HttpUtils.get("/user/" + serverResp.getString("id"), null, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response2) {
                                        Log.d(TAG, "------- this is the user response: " + response2);
                                        try {
                                            JSONObject serverResp2 = new JSONObject(response2.toString());

                                            String name = serverResp2.getString("name");
                                            String gender = serverResp2.getString("gender");

                                            db.updateUser(name, gender, null, null);

                                            HashMap hm = db.getUserDetails();
                                            String name3 = (String) hm.get("name");
                                            String gender3 = (String) hm.get("gender");
                                            String email3 = (String) hm.get("email");
                                            String created_at3 = (String) hm.get("created_at");
                                            Log.d(TAG, "login2-hash: name, gender, email, created_at: 1-" +
                                                    name3 +" 2-"+gender3+" 3-"+email3+" 4-"+created_at3);

                                            // Launch Dashboard Screen
                                            Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
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
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                            Log.d(TAG , "onFailure statusCode: "+ statusCode);
                            Log.d(TAG , "onFailure headers: "+ headers);
                            Log.d(TAG , "onFailure throwable: "+ throwable);
                            Log.d(TAG , "onFailure object: "+ object);
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