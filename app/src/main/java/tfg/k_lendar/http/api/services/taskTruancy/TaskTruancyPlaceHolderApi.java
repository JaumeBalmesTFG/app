package tfg.k_lendar.http.api.services.taskTruancy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import tfg.k_lendar.http.models.taskTruency.HomeModules;

public interface TaskTruancyPlaceHolderApi {

    @GET("module/all/ufs")
    Call<HomeModules> getAllUfs(@Header("Authorization") String token);

}

