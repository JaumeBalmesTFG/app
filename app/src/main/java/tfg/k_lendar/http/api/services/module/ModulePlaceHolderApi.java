package tfg.k_lendar.http.api.services.module;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ModulePlaceHolderApi {

    @POST("module")
    Call<JsonObject> createModule(@Header("Authorization") String token, @Body JsonObject jsonObject);

    @PUT("module/{moduleId}")
    Call<JsonObject> updateModule(@Header("Authorization") String token, @Path("moduleId") String moduleId, @Body JsonObject jsonObject);

}
