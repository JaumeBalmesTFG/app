package tfg.k_lendar.http.api.services.rule;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tfg.k_lendar.http.models.rule.ResponseRulesFromUf;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;

public interface RulePlaceHolderApi {

    @GET("rule/uf/{ufId}")
    Call<ResponseRulesFromUf> getRulesFromUf(@Header("Authorization") String token, @Path("ufId") String ufId);
}

