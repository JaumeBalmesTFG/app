package tfg.k_lendar.views.truancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import tfg.k_lendar.http.api.services.taskTruency.TaskTruencyPlaceHolderApi;
import tfg.k_lendar.http.api.services.truancy.TruancyPlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.http.models.truancy.Truancy;
import tfg.k_lendar.http.models.truancy.TruancyRequest;

public class EditTruancyActivity extends AppCompatActivity {

    Context context;
    TextInputLayout subjectLayout;
    AutoCompleteTextView subjectDropDown;
    TextInputLayout ufLayout;
    AutoCompleteTextView ufDropDown;
    TextInputLayout hoursLayout;
    Button plus;
    Button minus;
    TextView hoursTextView;
    TextInputEditText reasonInput;
    Button save,remove,cancel;

    ArrayAdapter<Modules> subjectAdapter;
    ArrayAdapter<Uf> ufsAdapter;
    List<Modules> modules;
    Modules selectedModule;
    Subject selectedSubject;
    Uf selectedUf;
    int hoursValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_truancy);

        subjectLayout = findViewById(R.id.subjectMenuEditTruancy);
        subjectDropDown = findViewById(R.id.subjectDropDownEditTruancy);
        ufLayout = findViewById(R.id.ufMenuEditTruancy);
        ufDropDown = findViewById(R.id.ufDropDownEditTruancy);
        hoursLayout = findViewById(R.id.hoursLayoutEditTruancy);
        plus = findViewById(R.id.plusButtonEditTruancy);
        minus = findViewById(R.id.minusButtonEditTruancy);
        hoursTextView = findViewById(R.id.hoursTextViewEditTruancy);
        reasonInput = findViewById(R.id.reasonInputEditTruancy);
        save = findViewById(R.id.saveButtonEditTruancy);
        remove = findViewById(R.id.removeButtonEditTruancy);
        cancel = findViewById(R.id.cancelButtonEditTruancy);

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
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateTruancyForm()) {
                    postTruancyService(new TruancyRequest(selectedModule.getId(), selectedUf.getId(), "04-04-2022",String.valueOf(reasonInput.getText()),Integer.parseInt(String.valueOf(hoursTextView.getText()))));
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoursValue = Integer.parseInt(hoursTextView.getText().toString());
                int out = hoursValue+1;
                hoursTextView.setText(String.valueOf(out));

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoursValue = Integer.parseInt(hoursTextView.getText().toString());
                if (!(hoursValue == 0)) {
                    int out = hoursValue-1;
                    hoursTextView.setText(String.valueOf(out));
                }
            }
        });

    }

    private boolean validateTruancyForm() {
        if (selectedModule == null) {
            subjectLayout.setError("Select a valid subject");
            subjectDropDown.addTextChangedListener(new RemoveErrorTextWatcher(subjectLayout));
            return false;
        }
        if (selectedUf == null) {
            ufLayout.setError("Select a valid UF");
            ufDropDown.addTextChangedListener(new RemoveErrorTextWatcher(ufLayout));
            return false;
        }
        if (TextUtils.isEmpty(hoursTextView.getText())) {
            hoursLayout.setError("Hours is required");
            hoursTextView.addTextChangedListener(new RemoveErrorTextWatcher(hoursLayout));
            return false;
        }
        return true;
    }

    public void setSubjectsInDropdown(List<Modules> modules){
        this.modules = modules;
        subjectAdapter = new ArrayAdapter<>(context, R.layout.dropdown_item, modules);
        subjectDropDown.setAdapter(subjectAdapter);
    }

    public void setUfsInDropdown(Modules module){
        ufsAdapter = new ArrayAdapter<>(context, R.layout.dropdown_item, module.getUfs());
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

    public void postTruancyService(TruancyRequest truancyRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TruancyPlaceHolderApi truancyPlaceHolderApi = retrofit.create(TruancyPlaceHolderApi.class);

        Call<Truancy> call = truancyPlaceHolderApi.postTruancy(AuthBearerToken.getAuthBearerToken(context), truancyRequest);

        call.enqueue(new Callback<Truancy>() {
            @Override
            public void onResponse(Call<Truancy> call, Response<Truancy> response) {
                if (response.isSuccessful()) {
                    Truancy truancy = response.body();
                } else {
                    ToastError.execute(context, response.toString());
                }
            }
            @Override
            public void onFailure(Call<Truancy> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
            }
        });
    }

    }
