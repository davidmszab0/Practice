package root.assign2.myCountries.preferences;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import root.assign2.R;

/**
 * Created by grace on 07/01/17.
 */

public class SimplePreferenceFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.simple_prefs);

        final CheckBoxPreference checkboxPref = (CheckBoxPreference) getPreferenceManager().findPreference("checkbox_preference");
        final CheckBoxPreference checkboxPrefColor = (CheckBoxPreference) getPreferenceManager().findPreference("checkbox_preference_color");


        checkboxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.toString().equals("true")) {
                    Toast.makeText(getActivity(), "CB: " + "true",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "CB: " + "false",
                            Toast.LENGTH_SHORT).show();
                }
                Log.d("MyApp", "Pref " + preference.getKey() + " changed to " + newValue.toString());
                return true;
            }
        });

        checkboxPrefColor.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue.toString().equals("true")) {
                    View v = getView();
                    int backgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorPrimary);
                    v.setBackgroundColor(backgroundColor);
                } else {
                    View v = getView();
                    int backgroundColor = ContextCompat.getColor(v.getContext(), R.color.colorAccent);
                    v.setBackgroundColor(backgroundColor);
                }
                Log.d("MyApp", "Pref " + preference.getKey() + " changed to " + newValue.toString());
                return true;
            }
        });
    }
}
