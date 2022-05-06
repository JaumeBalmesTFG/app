package tfg.k_lendar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.http.api.services.task.TaskPlaceHolderApi;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.api.services.uf.UfPlaceHolderApi;
import tfg.k_lendar.http.models.task.Task;
import tfg.k_lendar.http.models.task.TaskRequest;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;
import tfg.k_lendar.http.models.uf.PostUf;
import tfg.k_lendar.http.models.uf.UfRequest;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPref.getString("token", "");
        Intent intent;
        Log.d("AQUI", "CASDASDS");
        postSaveTaskService(new PostTaskRequest("6273f286a0cccb5b1687bc26",
                "6273f2aba0cccb5b1687bc29",
                "ANDRII",
                9,
                "EXAMPLE DESCRIPTION",
                "2022-05-04"
                ));
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

        TaskPlaceHolderApi taskPlaceHolderApi = retrofit.create(TaskPlaceHolderApi.class);

        Call<PostTask> call = taskPlaceHolderApi.postTask("Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Im1pcXVlbGxpYW9AZ21haWwuY29tIiwiX2lkIjoiNjI3M2UzMGRhMGNjY2I1YjE2ODdiOGI3IiwiaWF0IjoxNjUxNzYxOTMzfQ.c12bNy_NW6PLWIUyogLsShT1OFcB8JRltIDD-igxKms", postTaskRequest);

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

    public void postCreateUfService(UfRequest ufRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi ufPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);

        Call<PostUf> call = ufPlaceHolderApi.postUf(ufRequest);

        call.enqueue(new Callback<PostUf>() {
            @Override
            public void onResponse(Call<PostUf> call, Response<PostUf> response) {
                if (response.isSuccessful()) {
                    PostUf postUf = response.body();
                    Log.d("AQUI", "METHOD POST");
                    Log.d("AQUI", "MESSAGE: "+postUf.getMessage());
                    Log.d("AQUI",postUf.getBody().toString());
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<PostUf> call, Throwable t) {}
        });
    }

    public void putEditUfService(UfRequest ufRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi ufPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);

        Call<PostUf> call = ufPlaceHolderApi.editUf(ufRequest);

        call.enqueue(new Callback<PostUf>() {
            @Override
            public void onResponse(Call<PostUf> call, Response<PostUf> response) {
                if (response.isSuccessful()) {
                    PostUf postUf = response.body();
                    Log.d("AQUI", "METHOD PUT");
                    Log.d("AQUI", "MESSAGE: "+postUf.getMessage());
                    Log.d("AQUI",postUf.getBody().toString());
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<PostUf> call, Throwable t) {}
        });
    }

    public void deleteDelUfService(UfRequest ufRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi ufPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);

        Call<PostUf> call = ufPlaceHolderApi.deleteUf(ufRequest);

        call.enqueue(new Callback<PostUf>() {
            @Override
            public void onResponse(Call<PostUf> call, Response<PostUf> response) {
                if (response.isSuccessful()) {
                    PostUf postUf = response.body();
                    Log.d("AQUI", "METHOD DELETE");
                    Log.d("AQUI", "MESSAGE: "+postUf.getMessage());
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<PostUf> call, Throwable t) {}
        });
    }

    public void editPutTaskService(TaskRequest taskRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskPlaceHolderApi taskPlaceHolderApi = retrofit.create(TaskPlaceHolderApi.class);

        Call<Task> call = taskPlaceHolderApi.editTask(taskRequest);

        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    Task task = response.body();
                    Log.d("AQUI", "METHOD PUT");
                    Log.d("AQUI", "MESSAGE: "+task.getMessage());
                    Log.d("AQUI", "MESSAGE: "+task.getBody().toString());
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Task> call, Throwable t) {}
        });
    }

}