package tfg.k_lendar.core.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthBearerToken {
     public static String getAuthBearerToken(Context context) {
         SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
         String token = sharedPref.getString("token", "");
         return "Bearer: " + token;
     }
}
