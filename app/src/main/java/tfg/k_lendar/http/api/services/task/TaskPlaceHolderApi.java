package tfg.k_lendar.http.api.services.task;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import tfg.k_lendar.http.models.task.Task;
import tfg.k_lendar.http.models.task.TaskRequest;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;

public interface TaskPlaceHolderApi {

    @POST("task/create")
    Call<PostTask> postTask(@Header("Authorization") String token, @Body PostTaskRequest postTaskRequest);

    @PUT("task/{uf_id}") //RUTA asi o task/{task_id}????
    Call<Task> editTask(@Body TaskRequest taskRequest);

    @GET("task/{uf_id}")
    Call<Task> getTask();

}
