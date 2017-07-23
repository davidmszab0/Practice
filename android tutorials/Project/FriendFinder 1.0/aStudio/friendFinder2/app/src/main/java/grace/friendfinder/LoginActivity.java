package grace.friendfinder;

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

    private Button btnLogin, btnRegister;
    private EditText inputEmail, inputPassword;
    private TextView textViewForgotPassword;
    private DatabaseHandler db = null;

    private String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegisterLogin);
        //textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (isNetworkAvailable() == true) {
                    loginRequests(email, password);
                } else {
                    Toast.makeText(LoginActivity.this,"The app couldn't connect to the internet. " +
                            "Please connect to the internet and " +
                            "resume the application!", Toast.LENGTH_LONG).show();
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

    private void loginRequests(String email, String password) {
        // check for login response
        try {
            RequestParams rp = new RequestParams();
            rp.add("email", email);
            rp.add("password", password);

            // get user for this email and password
            HttpUtils.get("/account", rp, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    Log.d(TAG, "------- account response: " + response);
                    try {
                        JSONObject serverResp = new JSONObject(response.toString());
                        String email = serverResp.getString("email");
                        String created_at = serverResp.getString("createdAt");
                        Integer user_id = serverResp.getInt("id");

                        // storing the user in the local SQLite db
                        db = new DatabaseHandler(getApplicationContext());
                        db.resetTables(); // first deleting all the rows
                        db.addUser(null, null, email, created_at, user_id);
                        Log.d(TAG, "Created user in the local SQLite db.");

                        if (db.getRowCount()) {
                            HashMap hm = db.getUserDetails();
                            String name2 = (String) hm.get("name");
                            String gender2 = (String) hm.get("gender");
                            String email2 = (String) hm.get("email");
                            String created_at2 = (String) hm.get("created_at");
                            Integer user_id2 =  Integer.parseInt((String) hm.get("user_id"));
                            Log.d(TAG, "login-hash: name, gender, email, created_at, user_id: 1-" +
                                    name2 +" 2-"+gender2+" 3-"+email2+" 4-"+created_at2+" 5-"+user_id2 );
                        }

                        HttpUtils.get("/user/" + serverResp.getString("id"), null, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response2) {
                                Log.d(TAG, "------- user response: " + response2);
                                try {
                                    JSONObject serverResp2 = new JSONObject(response2.toString());

                                    String name = serverResp2.getString("name");
                                    String gender = serverResp2.getString("gender");

                                    db.updateUser(name, gender, null, null, null);

                                    HashMap hm = db.getUserDetails();
                                    String name3 = (String) hm.get("name");
                                    String gender3 = (String) hm.get("gender");
                                    String email3 = (String) hm.get("email");
                                    String created_at3 = (String) hm.get("created_at");
                                    Integer user_id3 =  Integer.parseInt((String) hm.get("user_id"));
                                    Log.d(TAG, "login2-hash: name, gender, email, created_at, user_id: 1-" +
                                            name3 +" 2-"+gender3+" 3-"+email3+" 4-"+created_at3+" 5-"+user_id3);

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}