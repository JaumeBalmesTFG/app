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
import tfg.k_lendar.http.models.subject.SubjectArchiveRequest;
import tfg.k_lendar.http.models.subject.SubjectRequest;
import tfg.k_lendar.views.auth.AuthActivity;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPref.getString("token", "");
        Intent intent;
        //postNewSubject(new SubjectRequest("Programacion333", "E3E3E3"));
        archiveSubject(new SubjectArchiveRequest(),"id");
        /*postSaveTaskService(new PostTaskRequest("6273f286a0cccb5b1687bc26",
                "6273f2aba0cccb5b1687bc29",
                "ANDRII",
                9,
                "EXAMPLE DESCRIPTION",
                "2022-05-04"
                ));*/
        if (!token.equals("")) {
            intent = new Intent(this, NavigationActivity.class);
        } else {
            intent = new Intent(this, AuthActivity.class);
        }
        startActivity(intent);
    }

    public void postNewSubject(SubjectRequest subjectRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.createSubject("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",subjectRequest);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    Log.d("AQUI", subject.getMessage());
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

    public void editSubject(SubjectRequest subjectRequest,String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.editSubject("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",subjectRequest,id);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    Log.d("AQUI", subject.getMessage());
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

    public void archiveSubject(SubjectArchiveRequest subjectArchiveRequest, String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.archiveSubject("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",subjectArchiveRequest,id);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    Log.d("AQUI", subject.getMessage());
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