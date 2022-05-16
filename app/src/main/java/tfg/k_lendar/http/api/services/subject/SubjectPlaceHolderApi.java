package tfg.k_lendar.http.api.services.subject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tfg.k_lendar.http.models.subject.Subject;
import tfg.k_lendar.http.models.subject.SubjectRequest;

public interface SubjectPlaceHolderApi {

    @POST("module")
    Call<Subject> createSubject(@Header("Authorization") String token,@Body SubjectRequest subjectRequest);

    @PUT("module/{id}")
    Call<Subject> editSubject(@Header("Authorization") String token, @Body SubjectRequest subjectRequest,@Path("id") String id);


}
