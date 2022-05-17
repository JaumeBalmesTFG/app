package tfg.k_lendar.http.api.services.uf;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import tfg.k_lendar.http.models.uf.PostUf;
import tfg.k_lendar.http.models.uf.UfRequest;

public interface UfPlaceHolderApi {


    @POST("uf/create")
    Call<PostUf> postUf(@Header("Authorization") String token, @Body UfRequest ufRequest);

    @PUT("uf/{id}/edit")
    Call<PostUf> editUf(@Header("Authorization") String token,@Body UfRequest ufRequest,@Path("id") String id);

    @DELETE("uf/{id}/delete")
    Call<PostUf> deleteUf(@Header("Authorization") String token,@Body UfRequest ufRequest,@Path("id") String id);


}