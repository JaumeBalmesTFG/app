package tfg.k_lendar.views.task;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import javax.security.auth.Subject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.RemoveErrorTextWatcher;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.rule.RulePlaceHolderApi;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.models.rule.ResponseRulesFromUf;
import tfg.k_lendar.http.models.rule.Rule;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;
import tfg.k_lendar.http.models.taskTruency.Uf;

public class EditTaskActivity extends AppCompatActivity {

    Context context;
    TextInputLayout subjectMenu;
    AutoCompleteTextView subjectDropDown;
    TextInputLayout ufMenu;
    AutoCompleteTextView ufDropDown;
    TextInputLayout titleLayout;
    TextInputEditText titleInput;
    TextInputLayout descriptionLayout;
    TextInputEditText descriptionEditText;
    SwitchMaterial doneSwitch;
    TextInputEditText gradeInput;
    Button saveButton, removeButton, cancelButton;

    ArrayAdapter<Modules> subjectAdapter;
    ArrayAdapter<Uf> ufsAdapter;


    List<Modules> modules;
    Modules selectedModule;

    Subject selectedSubject;
    Uf selectedUf;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        subjectMenu = findViewById(R.id.subjectMenuEditTask);
        subjectDropDown = findViewById(R.id.subjectDropDownEditTask);
        ufMenu = findViewById(R.id.ufMenuEditTask);
        ufDropDown = findViewById(R.id.ufDropDownEditTask);
        titleLayout = findViewById(R.id.titleLayoutEditTask);
        titleInput = findViewById(R.id.titleInputEditTask);
        descriptionLayout = findViewById(R.id.descriptionLayoutEditTask);
        descriptionEditText = findViewById(R.id.descriptionInputEditTask);
        doneSwitch = findViewById(R.id.doneSwitch);
        gradeInput = findViewById(R.id.gradeInput);
        saveButton = findViewById(R.id.saveButtonEditTask);
        removeButton = findViewById(R.id.removeButtonEditTask);
        cancelButton = findViewById(R.id.cancelButtonEditTask);
        context = this;

        subjectDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Modules) {
                    selectedModule = (Modules) item;
                    setUfsInDropdown(selectedModule);
                }
            }
        });

        ufDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Uf) {
                    selectedUf = (Uf) item;
                    getAllRulesFromUfService(selectedUf.getId());
                }
            }
        });

    }

    private boolean validateFormAndSaveTask() {
        if (selectedSubject == null) {
            subjectMenu.setError("Select a valid subject");
            subjectDropDown.addTextChangedListener(new RemoveErrorTextWatcher(subjectMenu));
            return false;
        }
        if (selectedUf == null) {
            ufMenu.setError("Select a valid UF");
            ufDropDown.addTextChangedListener(new RemoveErrorTextWatcher(ufMenu));
            return false;
        }
        if (TextUtils.isEmpty(titleInput.getText())) {
            titleLayout.setError("Select a valid title");
            titleInput.addTextChangedListener(new RemoveErrorTextWatcher(titleLayout));
            return false;
        }
        if (TextUtils.isEmpty(descriptionEditText.getText())) {
            descriptionLayout.setError("Select a valid description");
            descriptionEditText.addTextChangedListener(new RemoveErrorTextWatcher(descriptionLayout));
            return false;
        }
        return true;
    }

    public void setSubjectsInDropdown(List<Modules> modules){
        this.modules = modules;
        subjectAdapter = new ArrayAdapter<Modules>(this, R.layout.dropdown_item, modules);
        subjectDropDown.setAdapter(subjectAdapter);
    }

    public void setUfsInDropdown(Modules module){
        ufsAdapter = new ArrayAdapter<Uf>(this, R.layout.dropdown_item, module.getUfs());
        ufDropDown.setAdapter(ufsAdapter);
    }

    public void getAllUfsFromModulesService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruencyPlaceHolderApi taskTruencyPlaceHolderApi = retrofit.create(TaskTruencyPlaceHolderApi.class);

        Call<HomeModules> call = taskTruencyPlaceHolderApi.getAllUfs(AuthBearerToken.getAuthBearerToken(context));

        call.enqueue(new Callback<HomeModules>() {
            @Override
            public void onResponse(Call<HomeModules> call, Response<HomeModules> response) {
                if (response.isSuccessful()) {
                    HomeModules homeModules = response.body();
                    List<Modules> modules = homeModules.getBody();
                    setSubjectsInDropdown(modules);
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

    public void getAllRulesFromUfService(String ufId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RulePlaceHolderApi rulePlaceHolderApi = retrofit.create(RulePlaceHolderApi.class);


        Call<ResponseRulesFromUf> call = rulePlaceHolderApi.getRulesFromUf(AuthBearerToken.getAuthBearerToken(context), ufId);

        call.enqueue(new Callback<ResponseRulesFromUf>() {
            @Override
            public void onResponse(Call<ResponseRulesFromUf> call, Response<ResponseRulesFromUf> response) {
                if (response.isSuccessful()) {
                    ResponseRulesFromUf responseRulesFromUf = response.body();
                    List<Rule> rules = responseRulesFromUf.getBody();
                   // setRulesInDropdown(rules);
                } else {
                    ToastError.execute(context, response.toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseRulesFromUf> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
            }
        });
    }

    public void saveTaskService(PostTaskRequest postTaskRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruencyPlaceHolderApi taskTruencyPlaceHolderApi = retrofit.create(TaskTruencyPlaceHolderApi.class);

        Call<PostTask> call = taskTruencyPlaceHolderApi.postUf(AuthBearerToken.getAuthBearerToken(context), postTaskRequest);

        call.enqueue(new Callback<PostTask>() {
            @Override
            public void onResponse(Call<PostTask> call, Response<PostTask> response) {
                if (response.isSuccessful()) {
                    PostTask postTask = response.body();
                } else {
                    ToastError.execute(context, response.toString());
                }
            }
            @Override
            public void onFailure(Call<PostTask> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
            }
        });
    }

}