package tfg.k_lendar.http.api.services.truancy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import tfg.k_lendar.http.models.truancy.Truancy;
import tfg.k_lendar.http.models.truancy.TruancyGet;
import tfg.k_lendar.http.models.truancy.TruancyGetRecieve;
import tfg.k_lendar.http.models.truancy.TruancyGetRequest;
import tfg.k_lendar.http.models.truancy.TruancyRequest;

public interface TruancyPlaceHolderApi {

    @PUT("truancy/create")
    Call<Truancy> editTruancy(@Header("Authorization") String token,@Body TruancyRequest truancyRequest);

    @GET("truancy/{{id}}")
    Call<TruancyGetRecieve> getTruancy(@Header("Authorization") String token,@Body TruancyGetRequest truancyGetRequest);
}
