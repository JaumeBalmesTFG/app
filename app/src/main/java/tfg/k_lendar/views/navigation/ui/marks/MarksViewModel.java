package tfg.k_lendar.views.navigation.ui.marks;

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
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.ApiClient;
import tfg.k_lendar.http.api.services.marks.MarksPlaceHolderApi;
import tfg.k_lendar.http.models.marks.AllModules;
import tfg.k_lendar.http.models.marks.MarksModules;
import tfg.k_lendar.views.navigation.NavigationActivity;

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

        api.getAllModules(AuthBearerToken.getAuthBearerToken(context)).enqueue(new Callback<AllModules>() {
            @Override
            public void onResponse(Call<AllModules> call, Response<AllModules> response) {
                if (response.isSuccessful()) {
                    List<MarksModules> modules = (List<MarksModules>) response.body().getBody();
                    setList(modules);
                    ToastSuccess.execute(context,"modules/all is successful", NavigationActivity.class);
                } else {
                    ToastError.execute(context, response.toString());
                }
            }
            @Override
            public void onFailure(Call<AllModules> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
            }
        });

    }
}