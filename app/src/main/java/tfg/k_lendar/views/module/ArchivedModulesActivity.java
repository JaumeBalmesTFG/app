package tfg.k_lendar.views.module;

import static tfg.k_lendar.views.auth.AuthActivity.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.ApiClient;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.views.navigation.ui.subjects.archived.ListAdapter;

public class ArchivedModulesActivity extends AppCompatActivity {

    List<Modules> archivedModules = new ArrayList<>();
    ListAdapter listAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_subject);
         recyclerView = findViewById(R.id.recyclerViewArchived);
        getAllArchivedModules(this, this);

    }


    public void init() {



    }

    public void getAllArchivedModules(ArchivedModulesActivity archivedModulesActivity, Context context) {
        ModulePlaceHolderApi api = ApiClient.getClient(BASE_URL).create(ModulePlaceHolderApi.class);
        api.getAllArchivedModules(AuthBearerToken.getAuthBearerToken(context)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println(response);
                if (response.isSuccessful()) {
                    JsonObject body = response.body();
                    System.out.println(body);
                    JsonArray array = body.getAsJsonArray("body");
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject row = array.get(i).getAsJsonObject();
                        Modules module = new Modules(row.get("_id").getAsString(),
                                row.get("name").getAsString(),
                                row.get("color").getAsString(),
                                null);
                        archivedModules.add(module);
                    }
                    System.out.println(archivedModules);
                    listAdapter = new ListAdapter(archivedModules,context, archivedModulesActivity);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(listAdapter);

                    //TODO Llamar aqui a la función que ejecute el RecyclerView,
                    //TODO dentro de archivedModules estan todos los modulos
                } else {
                    System.out.println(response);
                    ToastError.execute(context, response.toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.getMessage());
                ToastError.execute(context, t.getMessage());
            }
        });
    }
}