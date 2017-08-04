package grace.friendfinder.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author David M Szabo on 01/08/2017.
 */

public class SimplePreferenceActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SimplePreferenceFragment())
                .commit();
    }
    @Override
    protected boolean isValidFragment (String fragmentName) {
        return (SimplePreferenceFragment.class.getName().equals(fragmentName));
    }
}
