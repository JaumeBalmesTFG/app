package tfg.k_lendar.http.api.services.module;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import tfg.k_lendar.http.models.module.ModulesResponse;

public interface ModulePlaceHolderApi {

    @GET("module/all/ufs")
    Call<ModulesResponse> getAllModulesAndUfs(@Header("Authorization") String token);
}
