package tfg.k_lendar.core.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthBearerToken {
     public static String getAuthBearerToken(Context context) {
         //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
         //String token = sharedPref.getString("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3YTc4YjU0M2U5NjQ1MmUxZmNkYmMxIiwiaWF0IjoxNjUyMTkzNDYxfQ.72d4aELj4YrCIEZdDDp0WntH_VbP_5k3qSFUr0u7ZIk");
         return "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg";
     }
}