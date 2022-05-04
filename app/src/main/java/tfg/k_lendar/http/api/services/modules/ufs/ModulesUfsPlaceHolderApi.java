package tfg.k_lendar.http.api.services.modules.ufs;

import retrofit2.Call;
import retrofit2.http.GET;
import tfg.k_lendar.http.models.modules.ufs.BaseRequest;

public interface ModulesUfsPlaceHolderApi {

    @GET("/module/all/ufs")
    Call<BaseRequest> getModulesUfs();
}
