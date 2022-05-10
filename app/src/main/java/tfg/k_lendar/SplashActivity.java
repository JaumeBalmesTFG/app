package tfg.k_lendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.http.api.services.subject.SubjectPlaceHolderApi;
import tfg.k_lendar.http.models.subject.Subject;
import tfg.k_lendar.http.models.subject.SubjectBody;
import tfg.k_lendar.http.models.subject.SubjectRequest;
import tfg.k_lendar.views.auth.AuthActivity;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        postNewSubjectService(new SubjectRequest("Mates","#1ADB61"));
        editSubjectService("624e1996f0b0c34f80987a73",new SubjectRequest("Programacion33", "#E3E3E3"));
        /*SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPref.getString("token", "");
        Intent intent;
        if (!token.equals("")) {
            intent = new Intent(this, NavigationActivity.class);
        } else {
            intent = new Intent(this, AuthActivity.class);
        }
        startActivity(intent);*/
    }

    public void postNewSubjectService(SubjectRequest subjectRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.createSubject("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1pcXVlbGxpYW9AZ21haWwuY29tIiwiX2lkIjoiNjI3M2UzMGRhMGNjY2I1YjE2ODdiOGI3IiwiaWF0IjoxNjUxNzYxOTMzfQ.c12bNy_NW6PLWIUyogLsShT1OFcB8JRltIDD-igxKms",subjectRequest);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    SubjectBody subjectBody = subject.getBody();
                    Log.d("AQUI", "METHOD POST");
                    Log.d("AQUI", "MESSAGE: "+subject.getMessage());
                    Log.d("AQUI", "Body: "+subjectBody.toString());
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Subject> call, Throwable t) {}
        });
    }

    public void editSubjectService(String id,SubjectRequest subjectRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.editSubject(id,"Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1pcXVlbGxpYW9AZ21haWwuY29tIiwiX2lkIjoiNjI3M2UzMGRhMGNjY2I1YjE2ODdiOGI3IiwiaWF0IjoxNjUxNzYxOTMzfQ.c12bNy_NW6PLWIUyogLsShT1OFcB8JRltIDD-igxKms",subjectRequest);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    SubjectBody subjectBody = subject.getBody();
                    Log.d("AQUI", "METHOD PUT");
                    Log.d("AQUI", "MESSAGE: "+subject.getMessage());
                    Log.d("AQUI", "Body: "+subjectBody.toString());
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Subject> call, Throwable t) {}
        });
    }

}