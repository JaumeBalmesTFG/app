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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.api.services.truancy.TruancyPlaceHolderApi;
import tfg.k_lendar.http.models.rule.Rule;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.http.models.truancy.Truancy;
import tfg.k_lendar.http.models.truancy.TruancyRequest;

public class NewTruancyFragment extends Fragment {
    ArrayAdapter<Modules> subjectsAdapter;
    ArrayAdapter<Uf> ufsAdapter;
    TextInputLayout subjectsMenu;
    AutoCompleteTextView subjectsDropdown;
    AutoCompleteTextView ufsDropdown;
    TextInputLayout ufsMenu;

    TextInputLayout hoursMenu;
    LinearLayoutCompat hoursContainer;

    LinearLayoutCompat ufsContainer;
    TextInputLayout hoursLayout;
    TextInputEditText hoursInput;
    TextInputLayout reasonLayout;
    TextInputEditText reasonInput;
    List<Modules> modules;
    Modules selectedModule;
    Uf selectedUf;
    Rule selectedRule;
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_truancy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        subjectsMenu = view.findViewById(R.id.menuSubjects);
        subjectsDropdown = view.findViewById(R.id.subjectsDropdown);
        ufsMenu = view.findViewById(R.id.menuUfs);
        ufsDropdown = view.findViewById(R.id.ufsDropdown);
        ufsContainer = view.findViewById(R.id.ufsContainer);
        hoursContainer = view.findViewById(R.id.hoursContainer);
        hoursLayout = view.findViewById(R.id.hoursLayout);
        hoursInput = view.findViewById(R.id.hoursInput);
        reasonLayout = view.findViewById(R.id.reasonLayout);
        reasonInput = view.findViewById(R.id.reasonInput);
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
                    hoursContainer.setVisibility(View.VISIBLE);
                }
            }
        });




        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFormAndSaveTask()) {
                    /*PostTaskRequest postTaskRequest = new PostTaskRequest(
                            selectedModule.getId(),
                            selectedUf.getId(),
                            selectedRule.getId(),
                            String.valueOf(hoursInput.getText()),
                            String.valueOf(reasonInput.getText()),
                            "2022-04-04"
                    );
                    saveTaskService(postTaskRequest);*/
                    postTruancyService(new TruancyRequest(selectedModule.getId(),"2022-04-04",reasonInput.getText().toString(),Integer.parseInt(hoursInput.getText().toString())));
                    System.out.println(selectedModule.getId() + "\n" +
                            selectedUf.getId() + "\n" +
                            String.valueOf(hoursInput.getText()) + "\n" +
                            String.valueOf(reasonInput.getText()));

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
        if (TextUtils.isEmpty(hoursInput.getText())) {
            hoursLayout.setError("Hours is required");
            hoursInput.addTextChangedListener(new RemoveErrorTextWatcher(hoursLayout));
            return false;
        }
        /*if (TextUtils.isEmpty(reasonInput.getText())) {
            reasonLayout.setError("Select a valid title");
            reasonInput.addTextChangedListener(new RemoveErrorTextWatcher(reasonLayout));
            return false;
        }*/
        return true;
    }

    public void setSubjectsInDropdown(List<Modules> modules){
        this.modules = modules;
        subjectsAdapter = new ArrayAdapter<>(this.getContext(), R.layout.dropdown_item, modules);
        subjectsDropdown.setAdapter(subjectsAdapter);
    }

    public void setUfsInDropdown(Modules module){
        ufsAdapter = new ArrayAdapter<Uf>(this.getContext(), R.layout.dropdown_item, module.getUfs());
        ufsDropdown.setAdapter(ufsAdapter);
    }

    /*public void setRulesInDropdown(List<Rule> rules) {
        rulesAdapter = new ArrayAdapter<>(this.getContext(), R.layout.dropdown_item, rules);
        hoursInput.setAdapter(rulesAdapter);
    }*/

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



    public void postTruancyService(TruancyRequest truancyRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TruancyPlaceHolderApi truancyPlaceHolderApi = retrofit.create(TruancyPlaceHolderApi.class);

        Call<Truancy> call = truancyPlaceHolderApi.postTruancy(AuthBearerToken.getAuthBearerToken(getContext()), truancyRequest);

        call.enqueue(new Callback<Truancy>() {
            @Override
            public void onResponse(Call<Truancy> call, Response<Truancy> response) {
                if (response.isSuccessful()) {
                    Truancy truancy = response.body();
                    Log.d("AQUI",truancy.getMessage());
                } else {
                    ToastError.execute(getContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<Truancy> call, Throwable t) {
                ToastError.execute(getContext(), t.getMessage());
            }
        });
    }
}
