package tfg.k_lendar.views.shared.ui.main.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;

public class NewTaskFragment extends Fragment {
    ArrayAdapter<Modules> subjectsAdapter;
    ArrayAdapter<Uf> ufsAdapter;
    AutoCompleteTextView subjectsDropdown;
    AutoCompleteTextView ufsDropdown;
    LinearLayoutCompat ufsContainer;
    LinearLayoutCompat titleContainer;
    List<Modules> modules;
    Modules selectedModule;
    Uf selectedUf;
    TextInputEditText titleInput;
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        subjectsDropdown = view.findViewById(R.id.subjectsDropdown);
        ufsDropdown = view.findViewById(R.id.ufsDropdown);
        ufsContainer = view.findViewById(R.id.ufsContainer);
        titleContainer = view.findViewById(R.id.titleContainer);
        titleInput = view.findViewById(R.id.titleInput);
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
                    titleContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFormAndSaveTask();
            }
        });

    }

    private void validateFormAndSaveTask() {
        if (selectedModule == null) {

        }
        if (selectedUf == null) {

        }
        if (TextUtils.isEmpty(titleInput.getText())) {

        }

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
                if (response.isSuccessful()) {
                    HomeModules homeModules = response.body();
                    List<Modules> modules = homeModules.getBody();
                    setSubjectsInDropdown(modules);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT);
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
