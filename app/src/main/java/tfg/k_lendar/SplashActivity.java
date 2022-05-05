package tfg.k_lendar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;
import tfg.k_lendar.http.models.taskTruency.Uf;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPref.getString("token", "");
        Intent intent;
        Log.d("AQUI", "CASDASDS");
        getAllUfsFromModulesService();
        /*if (!token.equals("")) {
            intent = new Intent(this, NavigationActivity.class);
        } else {
            intent = new Intent(this, AuthActivity.class);
        }
        startActivity(intent);*/
    }

    public void postSaveTaskService(PostTaskRequest postTaskRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruencyPlaceHolderApi taskTruencyPlaceHolderApi = retrofit.create(TaskTruencyPlaceHolderApi.class);

        Call<PostTask> call = taskTruencyPlaceHolderApi.postUf(postTaskRequest);

        call.enqueue(new Callback<PostTask>() {
            @Override
            public void onResponse(Call<PostTask> call, Response<PostTask> response) {
                if (response.isSuccessful()) {
                    PostTask postTask = response.body();
                    Log.d("AQUI",postTask.getMessage());
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<PostTask> call, Throwable t) {}
        });
    }

    public void getAllUfsFromModulesService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruencyPlaceHolderApi taskTruencyPlaceHolderApi = retrofit.create(TaskTruencyPlaceHolderApi.class);

        Call<HomeModules> call = taskTruencyPlaceHolderApi.getAllUfs("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1pcXVlbGxpYW9AZ21haWwuY29tIiwiX2lkIjoiNjI3M2UzMGRhMGNjY2I1YjE2ODdiOGI3IiwiaWF0IjoxNjUxNzYxOTMzfQ.c12bNy_NW6PLWIUyogLsShT1OFcB8JRltIDD-igxKms");

        call.enqueue(new Callback<HomeModules>() {
            @Override
            public void onResponse(Call<HomeModules> call, Response<HomeModules> response) {
                Log.d("RESPONSE SIUSPLAU", response.toString());
                if (response.isSuccessful()) {
                    Log.d("AQUI 2", "AQUI ENTRO");

                    HomeModules homeModules = response.body();

                    Log.d("AQUI 2", response.body().toString());

                    List<Modules> modules = homeModules.getBody();
                    //List<Uf> ufs = modules.get(moduloSeleccionado).getUfs();
                    for (int i = 0; i < modules.size(); i++) {
                        Log.d("AQUI","name: "+modules.get(i).getName() +",  id: "+modules.get(i).getId());
                        Log.d("FIESTA", modules.get(i).getUfs().toString());
                    }
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<HomeModules> call, Throwable t) {
                Log.d("FAIL", t.getMessage());
            }
        });
    }
}