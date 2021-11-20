package ymoreau.boitier.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    // Static class
    private Settings() {
    }

    public static int questionCount(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String questionCountStr = sharedPrefs.getString("pref_questionsCount", "40");
        try {
            return Integer.parseInt(questionCountStr);
        } catch (NumberFormatException ignored) {
        }

        return 40;
    }

    public static boolean isPedagogic(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getBoolean("pref_pedagogic", false);
    }
}
