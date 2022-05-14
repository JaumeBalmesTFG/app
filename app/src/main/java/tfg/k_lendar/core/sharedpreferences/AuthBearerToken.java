package tfg.k_lendar.core.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthBearerToken {
     public static String getAuthBearerToken(Context context) {
         //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
         //String token = sharedPref.getString("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3YTc4YjU0M2U5NjQ1MmUxZmNkYmMxIiwiaWF0IjoxNjUyMTkzNDYxfQ.72d4aELj4YrCIEZdDDp0WntH_VbP_5k3qSFUr0u7ZIk");
         return "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXIxQGdtYWlsLmNvbSIsIl9pZCI6IjYyODAwMzY5YjQ3Y2VlNDBlOTQ3N2QxZCIsImlhdCI6MTY1MjU1NjY0OX0.MOCtXwwRhlmp6mp_q31zzRBHbtxhaube3ok3eExoeEI";
     }
}
