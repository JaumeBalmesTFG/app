package tfg.k_lendar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.models.module.AllModules;
import tfg.k_lendar.http.models.module.ModulesResponse;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;
import tfg.k_lendar.http.models.taskTruency.Uf;
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

    public void getAllModulesAndUfs(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModulePlaceHolderApi modulePlaceHolderApi = retrofit.create(ModulePlaceHolderApi.class);

        Call<ModulesResponse> call = modulePlaceHolderApi.getAllModulesAndUfs("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg");

        call.enqueue(new Callback<ModulesResponse>() {
            @Override
            public void onResponse(Call<ModulesResponse> call, Response<ModulesResponse> response) {
                if (response.isSuccessful()) {
                    ModulesResponse modulesResponse = response.body(); // Contiene el mensaje y lista de modulos
                    List<AllModules> allModules = modulesResponse.getBody();// Lista de modulos

                    if(!allModules.isEmpty()){ // Recorre toda la lista y enseña modulos y sus ufs
                        for(AllModules m: allModules){
                            System.out.println(m);
                            List<Uf> ufs = m.getUfs();
                            if(!ufs.isEmpty()){
                                for(Uf u: ufs){
                                    System.out.println(u);
                                }
                            }
                        }
                    }

                    ToastSuccess.execute(getApplicationContext(),"Modules and Ufs recived Succcessfuly",SplashActivity.class);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<ModulesResponse> call, Throwable t) {}
        });
    }

}