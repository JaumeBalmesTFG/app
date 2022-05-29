package tfg.k_lendar.http.api.services.truancy;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tfg.k_lendar.http.models.truancy.Truancy;
import tfg.k_lendar.http.models.truancy.TruancyRequest;

public interface TruancyPlaceHolderApi {

    @POST("truancy/create")
    Call<Truancy> postTruancy(@Header("Authorization") String token, @Body TruancyRequest truancyRequest);

    @PUT("truancy/{truancyId}/edit")
    Call<JsonObject> updateTruancy(@Header("Authorization") String token, @Body JsonObject jsonObject, @Path("truancyId") String truancyId);

    @DELETE("truancy/{truancyId}/delete")
    Call<JsonObject> deleteTruancy(@Header("Authorization") String token,  @Path("truancyId") String truancyId);

    @GET("truancy/{truancyId}")
    Call<JsonObject> getTruancy(@Header("Authorization") String token, @Path("truancyId") String truancyId);
}
