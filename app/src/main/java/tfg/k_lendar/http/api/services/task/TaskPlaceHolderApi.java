package tfg.k_lendar.http.api.services.task;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import tfg.k_lendar.http.models.task.Task;
import tfg.k_lendar.http.models.task.TaskRequest;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;

public interface TaskPlaceHolderApi {

    @POST("task/create")
    Call<PostTask> postTask(@Header("Authorization") String token, @Body PostTaskRequest postTaskRequest);

    @PUT("task/{id}")
    Call<Task> editTask(@Query("id") String id, @Header("Authorization") String token, @Body TaskRequest taskRequest);

    @GET("task/{id}")
    Call<Task> getTask(@Query("id") String id,@Header("Authorization") String token);

}
