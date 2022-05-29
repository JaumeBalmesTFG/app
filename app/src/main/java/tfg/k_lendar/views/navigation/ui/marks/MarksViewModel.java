package tfg.k_lendar.views.navigation.ui.marks;

import static tfg.k_lendar.views.auth.AuthActivity.BASE_URL;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.ApiClient;
import tfg.k_lendar.http.api.services.marks.MarksPlaceHolderApi;
import tfg.k_lendar.http.models.marks.MarksModules;

public class MarksViewModel extends ViewModel {

    private MutableLiveData<List<MarksModules>> list;

    public MarksViewModel() {
        list = new MutableLiveData<>();
    }

    public LiveData<List<MarksModules>> getList() {
        return list;
    }

    public void setList(List<MarksModules> modulesList) {
        list.setValue(modulesList);
    }

    public void getAllModulesMarksTruanciesTasksGrades(Context context){
        MarksPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(MarksPlaceHolderApi.class);

        api.getAllModules(AuthBearerToken.getAuthBearerToken(context)).enqueue(new Callback<List<MarksModules>>() {
            @Override
            public void onResponse(Call<List<MarksModules>> call, Response<List<MarksModules>> response) {
                if (response.isSuccessful()) {
                    List<MarksModules> modules = response.body();
                    setList(modules);
                } else {
                    ToastError.execute(context, response.toString());
                }
            }
            @Override
            public void onFailure(Call<List<MarksModules>> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
            }
        });

    }
}