package tfg.k_lendar.core.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthBearerToken {
     public static String getAuthBearerToken(Context context) {
         SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
         String token = sharedPref.getString("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXIxMjNAZ21haWwuY29tIiwiX2lkIjoiNjI4MjcyMzNlMWE5MmExZjBlYTM2OTY4IiwiaWF0IjoxNjUyNzE2MDgzfQ.7QjT8dOrGNSxX0lBz_Q1V_AlKlrizsVx8zxDJvaT4-I");
         return "Bearer " + token;
     }
}
