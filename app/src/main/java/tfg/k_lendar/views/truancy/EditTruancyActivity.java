package tfg.k_lendar.views.truancy;

import static tfg.k_lendar.views.auth.AuthActivity.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.RemoveErrorTextWatcher;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.ApiClient;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.api.services.truancy.TruancyPlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.http.models.truancy.Truancy;
import tfg.k_lendar.http.models.truancy.TruancyRequest;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.shared.TodayTaskTruancyActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

public class EditTruancyActivity extends AppCompatActivity {

    String name, moduleId, ufId, id, reason;
    int hours;
    Button saveButton, removeButton, cancelButton, minusButton, plusButton;
    TextInputEditText hoursInput;
    ArrayAdapter<Modules> subjectsAdapter;
    ArrayAdapter<Uf> ufsAdapter;
    TextInputLayout subjectsMenu;
    AutoCompleteTextView subjectsDropdown;
    AutoCompleteTextView ufsDropdown;
    TextInputLayout ufsMenu;

    LinearLayoutCompat inputsLayout;
    LinearLayoutCompat hoursContainer;

    LinearLayoutCompat ufsContainer;
    TextInputLayout hoursLayout;
    TextInputLayout reasonLayout;
    TextInputEditText reasonInput;
    List<Modules> modules;
    Modules selectedModule;
    Uf selectedUf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_truancy);

        name = getIntent().getStringExtra("truancyName");
        id = getIntent().getStringExtra("truancyId");
        moduleId = getIntent().getStringExtra("moduleId");
        ufId = getIntent().getStringExtra("ufId");

        saveButton = findViewById(R.id.saveButtonEditTruancy);
        removeButton = findViewById(R.id.removeButtonEditTruancy);
        cancelButton = findViewById(R.id.cancelButtonEditTruancy);
        hoursInput = findViewById(R.id.hoursTextViewEditTruancy);
        minusButton = findViewById(R.id.minusEditTruancy);
        plusButton = findViewById(R.id.plusEditTruancy);
        subjectsMenu = findViewById(R.id.menuSubjectsT);
        subjectsDropdown = findViewById(R.id.subjectsDropdownT);
        ufsMenu = findViewById(R.id.menuUfsT);
        ufsDropdown = findViewById(R.id.ufsDropdownT);
        ufsContainer = findViewById(R.id.ufsContainerT);
        inputsLayout = findViewById(R.id.inputsLayoutT);
        hoursContainer = findViewById(R.id.hoursContainerT);
        reasonLayout = findViewById(R.id.reasonLayoutTedit);
        reasonInput = findViewById(R.id.reasonInputTEdit);

        getTask(id, getApplicationContext());

        subjectsDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Modules) {
                    selectedModule = (Modules) item;
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
                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateTruancyForm()) {
                    JsonObject updateTruancy = new JsonObject();
                    updateTruancy.addProperty("moduleId", moduleId);
                    updateTruancy.addProperty("ufId", ufId);
                    updateTruancy.addProperty("reason", String.valueOf(reasonInput.getText() != null ? reasonInput.getText() : ""));
                    updateTruancy.addProperty("hours", Integer.parseInt(String.valueOf(hoursInput.getText())));
                    updateTruancy.addProperty("date", TodayTaskTruancyActivity.calendarDate);
                    saveTruancyService(updateTruancy, getApplicationContext(), id);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTruancyActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTruancy(id, getApplicationContext());
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value= hoursInput.getText().toString();
                int finalValue = Integer.parseInt(value);
                String fv = String.valueOf(finalValue);
                if (finalValue<1){
                    finalValue=0;
                    hoursInput.setText(fv);
                } else {
                    finalValue--;
                    fv = String.valueOf(finalValue);
                    hoursInput.setText(fv);
                }

            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = hoursInput.getText().toString();
                int finalValue = Integer.parseInt(value);
                finalValue++;
                String fv = String.valueOf(finalValue);
                hoursInput.setText(fv);
            }
        });
    }

    private boolean validateTruancyForm() {
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
        return true;
    }

    public void setSubjectsInDropdown(List<Modules> modules){
        this.modules = modules;
        subjectsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, modules);
        subjectsDropdown.setAdapter(subjectsAdapter);
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getId().equals(moduleId)) {
                subjectsDropdown.setText(modules.get(i).getName());
                subjectsDropdown.showDropDown();
                subjectsDropdown.setSelection(i);
                subjectsDropdown.setListSelection(i);
                subjectsDropdown.performCompletion();
                setUfsInDropdown(modules.get(i));
                break;
            }
        }
    }

    public void setUfsInDropdown(Modules module){
        ufsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, module.getUfs());
        ufsDropdown.setAdapter(ufsAdapter);
        List<Uf> ufs = module.getUfs();
        for (int i = 0; i < ufs.size(); i++) {
            if (ufs.get(i).getId().equals(ufId)) {
                ufsDropdown.setText(ufs.get(i).getName());
                ufsDropdown.showDropDown();
                ufsDropdown.setSelection(i);
                ufsDropdown.setListSelection(i);
                ufsDropdown.performCompletion();
                break;
            }
        }
    }

    public void getAllUfsFromModulesService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruencyPlaceHolderApi taskTruencyPlaceHolderApi = retrofit.create(TaskTruencyPlaceHolderApi.class);
        Call<HomeModules> call = taskTruencyPlaceHolderApi.getAllUfs(AuthBearerToken.getAuthBearerToken(getApplicationContext()));

        call.enqueue(new Callback<HomeModules>() {
            @Override
            public void onResponse(Call<HomeModules> call, Response<HomeModules> response) {
                if (response.isSuccessful()) {
                    HomeModules homeModules = response.body();
                    List<Modules> modules = homeModules.getBody();
                    setSubjectsInDropdown(modules);
                } else {
                    ToastError.execute(getApplicationContext(), response.toString());

                }
            }
            @Override
            public void onFailure(Call<HomeModules> call, Throwable t) {
                ToastError.execute(getApplicationContext(), t.getMessage());
            }
        });
    }

    public void postTruancyService(TruancyRequest truancyRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TruancyPlaceHolderApi truancyPlaceHolderApi = retrofit.create(TruancyPlaceHolderApi.class);

        Call<Truancy> call = truancyPlaceHolderApi.postTruancy(AuthBearerToken.getAuthBearerToken(getApplicationContext()), truancyRequest);

        call.enqueue(new Callback<Truancy>() {
            @Override
            public void onResponse(Call<Truancy> call, Response<Truancy> response) {
                if (response.isSuccessful()) {
                    Truancy truancy = response.body();
                    ToastSuccess.execute(getApplicationContext(), response.message(), NavigationActivity.class);
                } else {
                    ToastError.execute(getApplicationContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<Truancy> call, Throwable t) {
                ToastError.execute(getApplicationContext(), t.getMessage());
            }
        });
    }

    public void getTask(String truancyId, Context context){

        TruancyPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(TruancyPlaceHolderApi.class);

        api.getTruancy(AuthBearerToken.getAuthBearerToken(context), truancyId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject body = response.body();
                    JsonObject truancy = body.get("body").getAsJsonObject();
                    hours = truancy.get("hours").getAsInt();
                    reason = truancy.get("reason").getAsString();
                    hoursInput.setText(String.valueOf(hours));
                    reasonInput.setText(reason);
                    getAllUfsFromModulesService();
                } else {
                    ToastError.execute(getApplicationContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(getApplicationContext(), t.getMessage());
            }
        });
    }

    public void saveTruancyService(JsonObject truancy, Context context, String truancyId){

        TruancyPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(TruancyPlaceHolderApi.class);

        api.updateTruancy(AuthBearerToken.getAuthBearerToken(context),truancy, truancyId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject postTask = response.body();
                    ToastError.execute(context, response.message());
                    finish();
                } else {
                    ToastError.execute(context, response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
            }
        });
    }

    public void deleteTruancy(String truancyId, Context context){

        TruancyPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(TruancyPlaceHolderApi.class);

        api.deleteTruancy(AuthBearerToken.getAuthBearerToken(context), truancyId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject postTask = response.body();
                    ToastError.execute(getApplicationContext(), response.toString());
                    Intent intent = new Intent(context, NavigationActivity.class);
                    startActivity(intent);

                } else {
                    ToastError.execute(getApplicationContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(getApplicationContext(), t.getMessage());
            }
        });
    }
}