package tfg.k_lendar.http.api.services.taskTruency;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tfg.k_lendar.http.api.requests.auth.AuthRequest;
import tfg.k_lendar.http.models.auth.Auth;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;

public interface TaskTruencyPlaceHolderApi {

    @GET("module/all/ufs")
    Call<HomeModules> getAllUfs(@Header("Authorization") String token);

    @POST("task/create")
    Call<PostTask> postUf(@Header("Authorization") String token, @Body PostTaskRequest postTaskRequest);

    @GET("task/{taskId}")
    Call<JsonObject> getTask(@Header("Authorization") String token,  @Path("taskId") String taskId);

    @PUT("task/{taskId}/edit")
    Call<JsonObject> updateTask(@Header("Authorization") String token, @Body JsonObject postTaskRequest, @Path("taskId") String moduleId);

    @DELETE("task/{taskId}/delete")
    Call<JsonObject> deleteTask(@Header("Authorization") String token,  @Path("taskId") String taskId);

}

