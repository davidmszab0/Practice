package root.assign2.myCountries.preferences;

import android.preference.PreferenceActivity;
import java.util.List;
import root.assign2.R;

/**
 * Created by grace on 07/01/17.
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
