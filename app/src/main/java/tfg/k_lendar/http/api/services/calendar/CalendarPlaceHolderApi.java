package tfg.k_lendar.http.api.services.calendar;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CalendarPlaceHolderApi {
    @GET("calendar")
    Call<JsonObject> getTasksTruanciesFromCalendar(@Header("Authorization") String token);
}

