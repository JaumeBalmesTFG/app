package tfg.k_lendar.http.api.services.taskTruancy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.HomeModulesAggregate;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;

public interface TaskTruancyPlaceHolderApi {

    @GET("module/all/ufs")
    Call<HomeModules> getAllUfs(@Header("Authorization") String token);

    @GET("module/all/ufs")
    Call<HomeModulesAggregate> getAllUfsAggregate(@Header("Authorization") String token);

    @POST("task/create")
    Call<PostTask> postUf(@Header("Authorization") String token, @Body PostTaskRequest postTaskRequest);

}

