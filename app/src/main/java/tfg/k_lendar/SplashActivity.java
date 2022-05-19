package tfg.k_lendar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.http.models.marks.AllModules;
import tfg.k_lendar.http.models.marks.ModulesMarks;
import tfg.k_lendar.views.auth.AuthActivity;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String token = AuthBearerToken.getAuthBearerToken(getApplicationContext());
        Intent intent;
        if (!token.equals("")) {
            intent = new Intent(this, NavigationActivity.class);
        } else {
            intent = new Intent(this, AuthActivity.class);
        }
        startActivity(intent);
    }

    public void getAllModules(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModulePlaceHolderApi modulePlaceHolderApi = retrofit.create(ModulePlaceHolderApi.class);

        Call<AllModules> call = modulePlaceHolderApi.getAllModules("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg");

        call.enqueue(new Callback<AllModules>() {
            @Override
            public void onResponse(Call<AllModules> call, Response<AllModules> response) {
                if (response.isSuccessful()) {
                    List<ModulesMarks> modules = response.body().getBody();

                    ToastSuccess.execute(getApplicationContext(),"modules/all successful",SplashActivity.class);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<AllModules> call, Throwable t) {}
        });
    }

}