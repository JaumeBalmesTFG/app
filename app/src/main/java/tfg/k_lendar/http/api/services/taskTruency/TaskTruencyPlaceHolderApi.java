package tfg.k_lendar.http.api.services.taskTruency;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tfg.k_lendar.http.api.requests.auth.AuthRequest;
import tfg.k_lendar.http.models.auth.Auth;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;

public interface TaskTruencyPlaceHolderApi {

    @GET("module/all/ufs")
    Call<HomeModules> getAllUfs(@Header("Authorization") String token);

    @POST("task/create")
    Call<PostTask> postUf(@Body PostTaskRequest postTaskRequest);

}

