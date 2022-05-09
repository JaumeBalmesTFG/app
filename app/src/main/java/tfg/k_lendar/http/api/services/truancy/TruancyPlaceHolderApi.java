package tfg.k_lendar.http.api.services.truancy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tfg.k_lendar.http.api.requests.auth.AuthRequest;
import tfg.k_lendar.http.models.modules.ufs.BaseRequest;
import tfg.k_lendar.http.models.truancy.Truancy;
import tfg.k_lendar.http.models.truancy.TruancyRequest;

public interface TruancyPlaceHolderApi {

    @POST("truancy/create")
    Call<Truancy> postTruancy(@Header("Authorization") String token, @Body TruancyRequest truancyRequest);
}
