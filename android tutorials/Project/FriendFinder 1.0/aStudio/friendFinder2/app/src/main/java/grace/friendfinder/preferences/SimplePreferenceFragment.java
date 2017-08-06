package grace.friendfinder.preferences;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import grace.friendfinder.R;

/**
 * @author David M Szabo on 01/08/2017.
 */

public class SimplePreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.simple_prefs);

        final ListPreference listPreference = (ListPreference) getPreferenceManager().findPreference("list_color_preference");
        final CheckBoxPreference checkBoxPreference = (CheckBoxPreference) getPreferenceManager().findPreference("checkbox_preference_notify");

        listPreference.setOnPreferenceChangeListener((new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.toString().equals("1")) {
                    View v = getView();
                    int backgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorGrey);
                    v.setBackgroundColor(backgroundColor);

                } else if (newValue.toString().equals("2")) {
                    View v = getView();
                    int backgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorPrimary);
                    v.setBackgroundColor(backgroundColor);
                } else if (newValue.toString().equals("3")){
                    View v = getView();
                    int backgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorAccent);
                    v.setBackgroundColor(backgroundColor);
                } else {
                    View v = getView();
                    int backgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorWhite);
                    v.setBackgroundColor(backgroundColor);
                }
                return true;
            }
        }));

        checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (newValue.toString().equals("true")) {
                    Toast.makeText(getActivity(), "CheckBox is set to: " + "true",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "CheckBox is set to: " + "false",
                            Toast.LENGTH_SHORT).show();
                }
                Log.d("MyApp", "Pref " + preference.getKey() + " changed to " + newValue.toString());
                return true;
            }
        });
    }
}
