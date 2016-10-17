package xyz.abhaychauhan.cinebuff.cinebuff.utils;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import xyz.abhaychauhan.cinebuff.cinebuff.R;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sortby_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.
                getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object Value) {
        String stringValue = Value.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}
