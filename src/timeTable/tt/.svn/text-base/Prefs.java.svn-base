package timeTable.tt;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static String AUSBILDUNG = "prefAusbildung";
    private static String SEMSTER = "prefSemester";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("myprefs", 0);
    }

    public static String getAusbildungPref(Context context) {
        return getPrefs(context).getString(AUSBILDUNG, "0");
    }

    public static String getSemesterPref(Context context) {
        return getPrefs(context).getString(SEMSTER, "0");
    }

    public static void setAusbildungPref(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(AUSBILDUNG, value).commit();
    }

    public static void setSemesterPref(Context context, String value) {
        // perform validation etc..
        getPrefs(context).edit().putString(SEMSTER, value).commit();
    }
}