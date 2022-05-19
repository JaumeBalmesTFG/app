package tfg.k_lendar.http.api.services.marks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import tfg.k_lendar.http.models.marks.AllModules;

public interface MarksPlaceHolderApi {

    @GET("module/all")
    Call<AllModules> getAllModules(@Header("Authorization") String token);
}
