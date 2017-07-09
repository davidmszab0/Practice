package grace.friendfinder;

import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import grace.friendfinder.utils.DatabaseHandler;
import grace.friendfinder.R;

public class MainActivity extends AppCompatActivity {

    boolean userLoggedIn = false;
    private String TAG = "Main";
    private DatabaseHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);

        db = new DatabaseHandler(getApplicationContext());

        if (db.getRowCount()) {
            setContentView(R.layout.activity_main);
            Log.d(TAG, "on the mainActivity screen");

        } else {
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing main activity screen
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // An XML based main_menu specification.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.icon_frame:  // Predefined icon ID
                db.resetTables();
                return true;
            case android.R.id.selectAll:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
