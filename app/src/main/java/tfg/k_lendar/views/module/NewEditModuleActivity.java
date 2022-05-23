package tfg.k_lendar.views.module;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class NewEditModuleActivity extends AppCompatActivity {

    String action;
    String selectedColor;
    String id;
    String title;
    JsonObject data = new JsonObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_edit_subject_dialog);
        TextInputEditText nameInput = findViewById(R.id.nameInput);
        Button saveButton = findViewById(R.id.saveButton);
        List<Button> buttons = new ArrayList<>(
                Arrays.asList(
                        findViewById(R.id.subjectColor1),
                        findViewById(R.id.subjectColor2),
                        findViewById(R.id.subjectColor3),
                        findViewById(R.id.subjectColor4),
                        findViewById(R.id.subjectColor5),
                        findViewById(R.id.subjectColor6),
                        findViewById(R.id.subjectColor7),
                        findViewById(R.id.subjectColor8)
                )
        );

        action = "create";
        id = "";
        String moduleId = getIntent().getStringExtra("id") != null ? getIntent().getStringExtra("id") : "";
        if (!(moduleId.equals(""))) {
            action = "edit";
            selectedColor = getIntent().getStringExtra("color");
            title = getIntent().getStringExtra("title");
            id = getIntent().getStringExtra("id");
            nameInput.setText(title);
        }

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedColor = view.getTag().toString();
                }
            });
        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.add("name", new JsonParser().parse(String.valueOf(nameInput.getText())));
                data.addProperty("color", selectedColor);
                createOrSaveModuleService(
                    data, id, action
                );
            }
        });
    }

    public void createOrSaveModuleService(JsonObject object, String id, String action){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModulePlaceHolderApi ModulePlaceHolderApi = retrofit.create(ModulePlaceHolderApi.class);


        Call<JsonObject> call;
        if (action.equals("create")) {
            call = ModulePlaceHolderApi.createModule(AuthBearerToken.getAuthBearerToken(getApplicationContext()), object);
        } else {
            call = ModulePlaceHolderApi.updateModule(AuthBearerToken.getAuthBearerToken(getApplicationContext()), id, object);
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    ToastSuccess.execute(NewEditModuleActivity.this, response.message(), NavigationActivity.class);
                } else {
                    ToastError.execute(NewEditModuleActivity.this, response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(NewEditModuleActivity.this, t.getMessage());
            }
        });
    }
}