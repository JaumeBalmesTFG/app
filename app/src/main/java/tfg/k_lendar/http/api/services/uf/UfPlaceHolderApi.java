package tfg.k_lendar.http.api.services.uf;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import tfg.k_lendar.http.models.uf.PostUf;
import tfg.k_lendar.http.models.uf.UfRequest;

public interface UfPlaceHolderApi {


    @POST("uf/create")
    Call<PostUf> postUf(@Body UfRequest ufRequest);

    @PUT("uf/{uf_id}/edit") // RUTA CORRECTA????
    Call<PostUf> editUf(@Body UfRequest ufRequest);

    @DELETE("uf/{uf_id}/delete") // RUTA CORRECTA?
    Call<PostUf> deleteUf(@Body UfRequest ufRequest);


}
