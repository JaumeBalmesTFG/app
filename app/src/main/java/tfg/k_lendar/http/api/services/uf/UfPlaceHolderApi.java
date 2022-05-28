package tfg.k_lendar.http.api.services.uf;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UfPlaceHolderApi {

    @POST("uf/create")
    Call<JsonObject> createUf(@Header("Authorization") String token, @Body JsonObject jsonObject);

    @PUT("uf/{ufId}")
    Call<JsonObject> updateUf(@Header("Authorization") String token, @Path("ufId") String ufId, @Body JsonObject jsonObject);

    @DELETE("uf/{ufId}")
    Call<JsonObject> deleteUf(@Header("Authorization") String token, @Path("ufId") String ufId);

}

