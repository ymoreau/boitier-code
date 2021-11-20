package ymoreau.boitier;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

import ymoreau.boitier.data.DataModel;

/**
 * Manages the user interface for settings of the app.
 *
 * @author Yoann Moreau
 */
public class SettingsActivity extends PreferenceActivity {

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        addPreferencesFromResource(R.xml.preferences);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        ListPreference listQuestionCount = (ListPreference) findPreference("pref_questionsCount");
        // Default value if empty
        if (listQuestionCount.getValue() == null)
            listQuestionCount.setValueIndex(0);
        // Set the summary with current value
        listQuestionCount.setSummary(listQuestionCount.getValue());

        // Set the listener to update the summary
        listQuestionCount.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());

                try {
                    int newCount = Integer.parseInt(newValue.toString());
                    if (DataModel.ref().answersCount() != newCount)
                        DataModel.ref().reset();
                    return true;
                } catch (NumberFormatException ignored) {
                }

                return false;
            }
        });
    }
}
