package tfg.k_lendar.core.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthBearerToken {
     public static String getAuthBearerToken(Context context) {
         SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
         String token = sharedPref.getString("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3OTI1ZTFiOTgzNGY5ZGVhYTdhY2M4IiwiaWF0IjoxNjUyMTA2NzIxfQ.gNGUmUKsMP0V6aGUgJQkA2O58dXKvAH-XK-QzvrCYAc");
         return "Bearer " + token;
     }
}
