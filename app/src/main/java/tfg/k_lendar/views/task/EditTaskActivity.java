package tfg.k_lendar.views.task;

import static tfg.k_lendar.views.auth.AuthActivity.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
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
import tfg.k_lendar.http.api.services.rule.RulePlaceHolderApi;
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.models.rule.ResponseRulesFromUf;
import tfg.k_lendar.http.models.rule.Rule;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.PostTask;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.views.auth.AuthActivity;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.shared.TaskTruancyActivity;
import tfg.k_lendar.views.shared.TodayTaskTruancyActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

public class EditTaskActivity extends AppCompatActivity {

    Button saveButton, removeButton, cancelButton;
    String name, moduleId, ufId, ruleId, id, grade, description;

    ArrayAdapter<Modules> subjectsAdapter;
    ArrayAdapter<Uf> ufsAdapter;
    ArrayAdapter<Rule> rulesAdapter;
    TextInputLayout subjectsMenu;
    AutoCompleteTextView subjectsDropdown;
    AutoCompleteTextView ufsDropdown;
    AutoCompleteTextView rulesDropdown;
    TextInputLayout ufsMenu;
    TextView taskNameTitle;

    TextInputLayout rulesMenu;
    LinearLayoutCompat rulesContainer;

    LinearLayoutCompat ufsContainer;
    LinearLayoutCompat titleContainer;
    TextInputLayout titleLayout;
    TextInputEditText titleInput;
    TextInputLayout descriptionLayout;
    TextInputEditText descriptionInput;
    TextInputLayout gradeLayout;
    TextInputEditText gradeInput;
    SwitchMaterial isDone;
    List<Modules> modules;
    Modules selectedModule;
    Uf selectedUf;
    Rule selectedRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        name = getIntent().getStringExtra("taskName");
        id = getIntent().getStringExtra("taskId");
        moduleId = getIntent().getStringExtra("moduleId");
        ufId = getIntent().getStringExtra("ufId");
        ruleId = getIntent().getStringExtra("ruleId");

        taskNameTitle = findViewById(R.id.taskTitle);
        taskNameTitle.setText(name);
        subjectsMenu = findViewById(R.id.menuSubjectsNewTask);
        subjectsDropdown = findViewById(R.id.subjectsDropdownNewTask);
        ufsMenu = findViewById(R.id.menuUfsNewTask);
        ufsDropdown = findViewById(R.id.ufsDropdownNewTask);
        ufsContainer = findViewById(R.id.ufsContainerNewTask);
        rulesContainer = findViewById(R.id.rulesContainerNewTask);
        rulesMenu = findViewById(R.id.menuRulesNewTask);
        rulesDropdown = findViewById(R.id.rulesDropdownNewTask);

        titleContainer = findViewById(R.id.titleContainerNewTask);
        titleLayout = findViewById(R.id.titleLayoutNewTask);
        titleInput = findViewById(R.id.titleInputNewTask);
        descriptionLayout = findViewById(R.id.descriptionLayoutNewTask);
        descriptionInput = findViewById(R.id.descriptionInputNewTask);

        gradeLayout = findViewById(R.id.gradeLayout);
        gradeInput = findViewById(R.id.gradeInput);

        saveButton = findViewById(R.id.saveButtonEditTask);
        removeButton = findViewById(R.id.removeButtonEditTask);
        cancelButton = findViewById(R.id.cancelButtonEditTask);

        isDone = findViewById(R.id.isDone);

        titleInput.setText(name);

        getTask(id, getApplicationContext());


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTaskActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(id, getApplicationContext());
            }
        });

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
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFormAndSaveTask()) {
                    JsonObject taskRequest = new JsonObject();
                    taskRequest.addProperty("moduleId", moduleId);
                    taskRequest.addProperty("ufId", ufId);
                    taskRequest.addProperty("ruleId", ruleId);
                    taskRequest.addProperty("name", String.valueOf(titleInput.getText()));
                    taskRequest.addProperty("grade", Integer.parseInt(String.valueOf(gradeInput.getText())));
                    taskRequest.addProperty("description", String.valueOf(descriptionInput.getText()));
                    taskRequest.addProperty("done", isDone.isChecked());
                    taskRequest.addProperty("dueDate", TodayTaskTruancyActivity.calendarDate);

                    saveTaskService(taskRequest,getApplicationContext(), id);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NavigationActivity.class);
                startActivity(intent);
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

        if (isDone.isChecked() && TextUtils.isEmpty(gradeInput.getText()) || isDone.isChecked() && Integer.parseInt(String.valueOf(gradeInput.getText())) > 10) {
            gradeLayout.setError("Set a grade higher than 0 and less or equal to 10");
            gradeInput.addTextChangedListener(new RemoveErrorTextWatcher(gradeLayout));
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
                System.out.println("FUNCIONA");
                subjectsDropdown.setText(modules.get(i).getName(), false);
                /*subjectsDropdown.showDropDown();
                subjectsDropdown.setSelection(i);
                subjectsDropdown.setListSelection(i);
                subjectsDropdown.performCompletion();*/
                setUfsInDropdown(modules.get(i));
                break;
            }
        }

    }

    public void setUfsInDropdown(Modules module){
        ufsAdapter = new ArrayAdapter<Uf>(getApplicationContext(), R.layout.dropdown_item, module.getUfs());
        ufsDropdown.setAdapter(ufsAdapter);
        List<Uf> ufs = module.getUfs();
        for (int i = 0; i < ufs.size(); i++) {
            if (ufs.get(i).getId().equals(ufId)) {
                System.out.println("FUNCIONA");
                ufsDropdown.setText(ufs.get(i).getName());
                ufsDropdown.showDropDown();
                ufsDropdown.setSelection(i);
                ufsDropdown.setListSelection(i);
                ufsDropdown.performCompletion();
                getAllRulesFromUfService(ufId);
                break;
            }
        }
    }

    public void setRulesInDropdown(List<Rule> rules) {
        rulesAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, rules);
        rulesDropdown.setAdapter(rulesAdapter);
        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).getId().equals(ruleId)) {
                System.out.println("FUNCIONA");
                rulesDropdown.setText(rules.get(i).getTitle());
                rulesDropdown.showDropDown();
                rulesDropdown.setSelection(i);
                rulesDropdown.setListSelection(i);
                rulesDropdown.performCompletion();
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

    public void getAllRulesFromUfService(String ufId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RulePlaceHolderApi rulePlaceHolderApi = retrofit.create(RulePlaceHolderApi.class);


        Call<ResponseRulesFromUf> call = rulePlaceHolderApi.getRulesFromUf(AuthBearerToken.getAuthBearerToken(getApplicationContext()), ufId);

        call.enqueue(new Callback<ResponseRulesFromUf>() {
            @Override
            public void onResponse(Call<ResponseRulesFromUf> call, Response<ResponseRulesFromUf> response) {
                if (response.isSuccessful()) {
                    ResponseRulesFromUf responseRulesFromUf = response.body();
                    List<Rule> rules = responseRulesFromUf.getBody();
                    setRulesInDropdown(rules);
                } else {
                    ToastError.execute(getApplicationContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseRulesFromUf> call, Throwable t) {
                ToastError.execute(getApplicationContext(), t.getMessage());
            }
        });
    }


    public void saveTaskService(JsonObject postTaskRequest, Context context, String taskId){

        TaskTruencyPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(TaskTruencyPlaceHolderApi.class);

        api.updateTask(AuthBearerToken.getAuthBearerToken(context),postTaskRequest, taskId).enqueue(new Callback<JsonObject>() {
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

    public void getTask(String taskId, Context context){

        TaskTruencyPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(TaskTruencyPlaceHolderApi.class);

        api.getTask(AuthBearerToken.getAuthBearerToken(context), taskId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    JsonObject postTask = response.body();
                    JsonObject task = postTask.get("body").getAsJsonObject();
                    if (!(task.get("grade").isJsonNull())) {
                        grade = task.get("grade").getAsJsonObject().get("$numberDecimal").getAsString();
                        gradeInput.setText(grade);
                    }

                    description = task.get("description").getAsString();

                    descriptionInput.setText(description);
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


    public void deleteTask(String taskId, Context context){

        TaskTruencyPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(TaskTruencyPlaceHolderApi.class);

        api.deleteTask(AuthBearerToken.getAuthBearerToken(context), taskId).enqueue(new Callback<JsonObject>() {
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