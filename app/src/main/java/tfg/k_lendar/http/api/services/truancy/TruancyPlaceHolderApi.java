package tfg.k_lendar.http.api.services.truancy;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import tfg.k_lendar.http.models.truancy.Truancy;
import tfg.k_lendar.http.models.truancy.TruancyGet;
import tfg.k_lendar.http.models.truancy.TruancyGetRecieve;
import tfg.k_lendar.http.models.truancy.TruancyGetRequest;
import tfg.k_lendar.http.models.truancy.TruancyRequest;

public interface TruancyPlaceHolderApi {

    @PUT("truancy/create")
    Call<Truancy> editTruancy(@Header("Authorization") String token,@Body TruancyRequest truancyRequest);

    @GET("truancy/{id}")
    Call<TruancyGetRecieve> getTruancy(@Query("id") String id, @Header("Authorization") String token);

    @DELETE("truancy/{id}/delete")
    Call<Truancy> deleteTruancy(@Query("id") String id, @Header("Authorization") String token);
}
