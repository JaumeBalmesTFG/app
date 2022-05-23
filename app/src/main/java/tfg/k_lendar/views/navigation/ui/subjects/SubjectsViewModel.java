package tfg.k_lendar.views.navigation.ui.subjects;

import static tfg.k_lendar.views.auth.AuthActivity.BASE_URL;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.ApiClient;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;

public class SubjectsViewModel extends ViewModel {

    private final MutableLiveData<List<Modules>> list;

    public SubjectsViewModel() {
        list = new MutableLiveData<>();
    }

    public LiveData<List<Modules>> getList() {
        return list;
    }

    public void setList(List<Modules> modulesList) {
        list.setValue(modulesList);
    }

    public void getAllUfsFromModulesService(Context context) {
        TaskTruencyPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(TaskTruencyPlaceHolderApi.class);
        api.getAllUfs(AuthBearerToken.getAuthBearerToken(context)).enqueue(new Callback<HomeModules>() {
            @Override
            public void onResponse(Call<HomeModules> call, Response<HomeModules> response) {
                if (response.isSuccessful()) {
                    HomeModules homeModules = response.body();
                    List<Modules> modules = homeModules.getBody();
                    setList(modules);

                } else {
                    ToastError.execute(context, response.toString());
                }
            }

            @Override
            public void onFailure(Call<HomeModules> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
            }
        });
    }
}