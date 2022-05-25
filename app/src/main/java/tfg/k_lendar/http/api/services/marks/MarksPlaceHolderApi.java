package tfg.k_lendar.http.api.services.marks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import tfg.k_lendar.http.models.marks.AllModules;
import tfg.k_lendar.http.models.marks.MarksModules;

public interface MarksPlaceHolderApi {

    @GET("module/all")
    Call<List<MarksModules>> getAllModules(@Header("Authorization") String token);
}
