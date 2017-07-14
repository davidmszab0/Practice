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
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.utils.HttpUtils;

/**
 * @author David M. Szabo
 */

public class RegisterActivity extends Activity {

    Button btnRegister;
    EditText nameEditText;
    EditText emailEditText;
    EditText genderEditText;
    EditText passwordEditText;

    private String genderInput ="";
    private String passwordInput = "";
    private String emailInput = "";
    private String nameInput = "";

    private String TAG = "Register";
    private DatabaseHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Importing all assets like buttons, text fields
        nameEditText = (EditText) findViewById(R.id.registerName);
        emailEditText = (EditText) findViewById(R.id.registerEmail);
        genderEditText = (EditText) findViewById(R.id.gender);
        passwordEditText = (EditText) findViewById(R.id.registerPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                final Context context = getApplicationContext();
                nameInput = nameEditText.getText().toString();
                emailInput = emailEditText.getText().toString();
                genderInput = genderEditText.getText().toString();
                passwordInput = passwordEditText.getText().toString();

                if (passwordInput.length() < 0) {
                    Toast.makeText(getApplicationContext(), "Password needs to be at least 1 character long", Toast.LENGTH_SHORT).show();
                    passwordEditText.setText("");
                }
                if (emailInput.length() < 0) {
                    Toast.makeText(getApplicationContext(), "Email needs to be at least 1 character long", Toast.LENGTH_SHORT).show();
                    emailEditText.setText("");
                }

                try {
                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("email", emailInput);
                    jsonParams.put("password", passwordInput);
                    StringEntity entityAccount = new StringEntity(jsonParams.toString());
                    Log.d(TAG, "entityAccount: " + entityAccount);

                    HttpUtils.post(context, "/account", entityAccount, "application/json", new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Log.d(TAG, "------- this is response : " + response);

                            try {
                                final JSONObject serverResp = new JSONObject(response.toString());

                                // storing the user in the local SQLite db
                                db = new DatabaseHandler(getApplicationContext());
                                db.resetTables(); // first deleting all the rows
                                db.addUser(null, null, serverResp.getString("email"),serverResp.getString("createdAt"));
                                Log.d(TAG, "Created user in the local SQLite db.");

                                    HashMap hm = db.getUserDetails();
                                    String name2 = (String) hm.get("name");
                                    String gender2 = (String) hm.get("gender");
                                    String email2 = (String) hm.get("email");
                                    String created_at2 = (String) hm.get("created_at");
                                    Log.d(TAG, "register-hash: name, gender, email, created_at: 1-" +
                                            name2 +" 2-"+gender2+" 3-"+email2+" 4-"+created_at2);

                                JSONObject jsonParams2 = new JSONObject();
                                jsonParams2.put("name", nameInput);
                                jsonParams2.put("gender", genderInput);
                                StringEntity entityUser = new StringEntity(jsonParams2.toString());
                                Log.d(TAG, "entityUser: " + entityUser);

                                HttpUtils.put(context, "/user/" + serverResp.getString("id"), entityUser, "application/json", new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response2) {
                                        Log.d(TAG, "------- this is response2 : " + response2);
                                        try {
                                            JSONObject serverResp2 = new JSONObject(response2.toString());
                                            db.updateUser(serverResp2.getString("name"), serverResp2.getString("gender"),
                                                   serverResp.getString("email"), serverResp.getString("createdAt"));

                                            HashMap hm = db.getUserDetails();
                                            String name2 = (String) hm.get("name");
                                            String gender2 = (String) hm.get("gender");
                                            String email2 = (String) hm.get("email");
                                            String created_at2 = (String) hm.get("created_at");
                                            Log.d(TAG, "register2-hash: name, gender, email, created_at: 1-" +
                                                    name2 +" 2-"+gender2+" 3-"+email2+" 4-"+created_at2);

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

                                // Launch Dashboard Screen
                                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                                // Close all views before launching Dashboard
                                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainActivityIntent);
                                // Close Login Screen
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
