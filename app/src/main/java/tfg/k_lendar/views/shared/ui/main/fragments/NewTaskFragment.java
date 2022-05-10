package tfg.k_lendar.views.shared.ui.main.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.List;

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

public class NewTaskFragment extends Fragment {
    ArrayAdapter<Modules> subjectsAdapter;
    ArrayAdapter<Uf> ufsAdapter;
    ArrayAdapter<Rule> rulesAdapter;
    TextInputLayout subjectsMenu;
    AutoCompleteTextView subjectsDropdown;
    AutoCompleteTextView ufsDropdown;
    AutoCompleteTextView rulesDropdown;
    TextInputLayout ufsMenu;

    TextInputLayout rulesMenu;
    LinearLayoutCompat rulesContainer;

    LinearLayoutCompat ufsContainer;
    LinearLayoutCompat titleContainer;
    TextInputLayout titleLayout;
    TextInputEditText titleInput;
    TextInputLayout descriptionLayout;
    TextInputEditText descriptionInput;
    List<Modules> modules;
    Modules selectedModule;
    Uf selectedUf;
    Rule selectedRule;
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        subjectsMenu = view.findViewById(R.id.menuSubjects);
        subjectsDropdown = view.findViewById(R.id.subjectsDropdown);
        ufsMenu = view.findViewById(R.id.menuUfs);
        ufsDropdown = view.findViewById(R.id.ufsDropdown);
        ufsContainer = view.findViewById(R.id.ufsContainer);
        rulesContainer = view.findViewById(R.id.rulesContainer);
        rulesMenu = view.findViewById(R.id.menuRules);
        rulesDropdown = view.findViewById(R.id.rulesDropdown);

        titleContainer = view.findViewById(R.id.titleContainer);
        titleLayout = view.findViewById(R.id.titleLayout);
        titleInput = view.findViewById(R.id.titleInput);
        descriptionLayout = view.findViewById(R.id.descriptionLayout);
        descriptionInput = view.findViewById(R.id.descriptionInput);
        saveButton = view.findViewById(R.id.saveButton);
        getAllUfsFromModulesService();
        subjectsDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Modules) {
                    selectedModule = (Modules) item;
                    ufsContainer.setVisibility(View.VISIBLE);
                    setUfsInDropdown(selectedModule);
                }
            }
        });

        ufsDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Uf) {
                    selectedUf = (Uf) item;
                    rulesContainer.setVisibility(View.VISIBLE);
                    getAllRulesFromUfService(selectedUf.getId());
                }
            }
        });

        rulesDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Rule) {
                    selectedRule = (Rule) item;
                    titleContainer.setVisibility(View.VISIBLE);
                }
            }
        });



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFormAndSaveTask()) {
                    PostTaskRequest postTaskRequest = new PostTaskRequest(
                        selectedModule.getId(),
                        selectedUf.getId(),
                            selectedRule.getId(),
                            String.valueOf(titleInput.getText()),
                            String.valueOf(descriptionInput.getText()),
                            "2022-04-04"
                    );
                    saveTaskService(postTaskRequest);
                }
            }
        });

    }

    private boolean validateFormAndSaveTask() {
        if (selectedModule == null) {
            subjectsMenu.setError("Select a valid subject");
            subjectsDropdown.addTextChangedListener(new RemoveErrorTextWatcher(subjectsMenu));
            return false;
        }
        if (selectedUf == null) {
            ufsMenu.setError("Select a valid UF");
            ufsDropdown.addTextChangedListener(new RemoveErrorTextWatcher(ufsMenu));
            return false;
        }
        if (selectedRule == null) {
            rulesMenu.setError("Select a valid Rule");
            rulesDropdown.addTextChangedListener(new RemoveErrorTextWatcher(rulesMenu));
            return false;
        }
        if (TextUtils.isEmpty(titleInput.getText())) {
            titleLayout.setError("Select a valid title");
            titleInput.addTextChangedListener(new RemoveErrorTextWatcher(titleLayout));
            return false;
        }
        if (TextUtils.isEmpty(descriptionInput.getText())) {
            descriptionLayout.setError("Select a valid title");
            descriptionInput.addTextChangedListener(new RemoveErrorTextWatcher(descriptionLayout));
            return false;
        }
        return true;
    }

    public void setSubjectsInDropdown(List<Modules> modules){
        this.modules = modules;
        subjectsAdapter = new ArrayAdapter<>(this.getContext(), R.layout.dropdown_item, modules);
        subjectsDropdown.setAdapter(subjectsAdapter);
    }

    public void setUfsInDropdown(Modules module){
        ufsAdapter = new ArrayAdapter<>(this.getContext(), R.layout.dropdown_item, module.getUfs());
        ufsDropdown.setAdapter(ufsAdapter);
    }

    public void setRulesInDropdown(List<Rule> rules) {
        rulesAdapter = new ArrayAdapter<>(this.getContext(), R.layout.dropdown_item, rules);
        rulesDropdown.setAdapter(rulesAdapter);
    }

    public void getAllUfsFromModulesService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruencyPlaceHolderApi taskTruencyPlaceHolderApi = retrofit.create(TaskTruencyPlaceHolderApi.class);

        Call<HomeModules> call = taskTruencyPlaceHolderApi.getAllUfs(AuthBearerToken.getAuthBearerToken(getContext()));

        call.enqueue(new Callback<HomeModules>() {
            @Override
            public void onResponse(Call<HomeModules> call, Response<HomeModules> response) {
                if (response.isSuccessful()) {
                    HomeModules homeModules = response.body();
                    List<Modules> modules = homeModules.getBody();
                    setSubjectsInDropdown(modules);
                } else {
                    ToastError.execute(getContext(), response.toString());

                }
            }
            @Override
            public void onFailure(Call<HomeModules> call, Throwable t) {
                ToastError.execute(getContext(), t.getMessage());
            }
        });
    }

    public void getAllRulesFromUfService(String ufId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RulePlaceHolderApi rulePlaceHolderApi = retrofit.create(RulePlaceHolderApi.class);


        Call<ResponseRulesFromUf> call = rulePlaceHolderApi.getRulesFromUf(AuthBearerToken.getAuthBearerToken(getContext()), ufId);

        call.enqueue(new Callback<ResponseRulesFromUf>() {
            @Override
            public void onResponse(Call<ResponseRulesFromUf> call, Response<ResponseRulesFromUf> response) {
                if (response.isSuccessful()) {
                    ResponseRulesFromUf responseRulesFromUf = response.body();
                    List<Rule> rules = responseRulesFromUf.getBody();
                    setRulesInDropdown(rules);
                } else {
                    ToastError.execute(getContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseRulesFromUf> call, Throwable t) {
                ToastError.execute(getContext(), t.getMessage());
            }
        });
    }

    public void saveTaskService(PostTaskRequest postTaskRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruencyPlaceHolderApi taskTruencyPlaceHolderApi = retrofit.create(TaskTruencyPlaceHolderApi.class);

        Call<PostTask> call = taskTruencyPlaceHolderApi.postUf(AuthBearerToken.getAuthBearerToken(getContext()), postTaskRequest);

        call.enqueue(new Callback<PostTask>() {
            @Override
            public void onResponse(Call<PostTask> call, Response<PostTask> response) {
                if (response.isSuccessful()) {
                    PostTask postTask = response.body();
                } else {
                    ToastError.execute(getContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<PostTask> call, Throwable t) {
                ToastError.execute(getContext(), t.getMessage());
            }
        });
    }
}
