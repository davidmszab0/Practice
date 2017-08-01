package grace.friendfinder.preferences;

import android.preference.PreferenceActivity;
import java.util.List;
import grace.friendfinder.R;

/**
 * @author David M Szabo on 01/08/2017.
 */

public class SimplePreferenceActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }
    @Override
    protected boolean isValidFragment (String fragmentName) {
        return (SimplePreferenceFragment.class.getName().equals(fragmentName));
    }
}
