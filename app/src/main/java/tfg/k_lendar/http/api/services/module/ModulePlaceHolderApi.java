package tfg.k_lendar.http.api.services.module;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import tfg.k_lendar.http.models.marks.AllModules;

public interface ModulePlaceHolderApi {

    @GET("module/all")
    Call<AllModules> getAllModules(@Header("Authorization") String token);
}
