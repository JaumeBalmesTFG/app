package tfg.k_lendar.views.uf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
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
import tfg.k_lendar.http.api.services.rule.RulePlaceHolderApi;
import tfg.k_lendar.http.api.services.uf.UfPlaceHolderApi;
import tfg.k_lendar.http.models.rule.ResponseRulesFromUf;
import tfg.k_lendar.http.models.rule.Rule;
import tfg.k_lendar.views.module.NewEditModuleActivity;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class NewUfActivity extends AppCompatActivity {

    Button saveButton, cancelButton, addRule;
    TextInputEditText titleInput, nameInput, percentatgeInput, hoursInput, truancyInput;
    TextInputLayout titleLayout, hoursLayout, truancyLayout, nameLayout, percentatgeLayout;
    LinearLayoutCompat rulesLayout;
    List<Rule> rulesList = new ArrayList<>();
   List<RelativeLayout> views = new ArrayList<>();
    List<Button> editButtons = new ArrayList<>();
    List<Button> deleteButtons = new ArrayList<>();
    int editingRule, editingPercentage;
    boolean isEditing, correctPercentage;
    String action;
    String moduleId = "62924d9f8b4a83e390f0df51";
    JsonArray lastRules = new JsonArray();
    String ufId;
    String name;
    String hours;
    String truancyPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_uf);

        saveButton = findViewById(R.id.saveButtonUf);
        cancelButton = findViewById(R.id.cancelButtonUf);
        titleLayout = findViewById(R.id.titleLayoutUf);
        titleInput = findViewById(R.id.titleInputUf);
        nameInput = findViewById(R.id.nameInputUf);
        nameLayout = findViewById(R.id.nameLayout);
        percentatgeInput = findViewById(R.id.percentatgesInputUf);
        percentatgeLayout = findViewById(R.id.percentatgesLayout);
        hoursLayout = findViewById(R.id.hoursLayoutUf);
        hoursInput = findViewById(R.id.hoursInputUf);
        truancyLayout = findViewById(R.id.truancyLayoutUf);
        truancyInput = findViewById(R.id.truancyInputUf);
        addRule = findViewById(R.id.addRule);
        rulesLayout = findViewById(R.id.rulesLayout);

        moduleId = getIntent().getStringExtra("moduleId") != null ? getIntent().getStringExtra("moduleId") : "";
        ufId = getIntent().getStringExtra("ufId") != null ? getIntent().getStringExtra("ufId") : "";
        action = getIntent().getStringExtra("action") != null ? getIntent().getStringExtra("action") : "create";
        name = getIntent().getStringExtra("name") != null ? getIntent().getStringExtra("name") : "";
        hours = getIntent().getStringExtra("hours") != null ? getIntent().getStringExtra("hours") : "";
        truancyPercentage = getIntent().getStringExtra("truancyPercentage") != null ? getIntent().getStringExtra("truancyPercentage") : "";

        if (action.equals("update")) {
            System.out.println("UPDATE");
            titleInput.setText(name);
            hoursInput.setText(String.valueOf(hours));
            truancyInput.setText(String.valueOf(truancyPercentage));
            getAllRulesService(ufId, getApplicationContext());
        }


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewUfActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFormUf()) {
                    Log.d("DAVID", "FUNCIONA SAVE BUTTON");
                    prepareCreateUpdateUfCall();
                }
            }
        });

        addRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateRules()) {
                    createCustomRule();
                }
            }
        });

    }

    private boolean validateRules() {
        if (TextUtils.isEmpty(nameInput.getText())) {
            nameLayout.setError("The name is required");
            nameInput.addTextChangedListener(new RemoveErrorTextWatcher(nameLayout));
            return false;
        }
        if (TextUtils.isEmpty(percentatgeInput.getText())) {
            percentatgeLayout.setError("The percentage is required");
            percentatgeInput.addTextChangedListener(new RemoveErrorTextWatcher(percentatgeLayout));
            return false;
        }

        if ((Integer.parseInt(String.valueOf(percentatgeInput.getText()))) > 100) {
            percentatgeLayout.setError("The percentage can not be more than 100");
            percentatgeInput.addTextChangedListener(new RemoveErrorTextWatcher(percentatgeLayout));
            return false;
        }

        if (!checkRulesSumDoLessThanAHundred(Integer.parseInt(String.valueOf(percentatgeInput.getText())))) {
            percentatgeLayout.setError("The total percentage can not be more than 100");
            percentatgeInput.addTextChangedListener(new RemoveErrorTextWatcher(percentatgeLayout));
            return false;
        }

        return true;
    }

    private boolean validateFormUf() {
        if (TextUtils.isEmpty(titleInput.getText())) {
            titleLayout.setError("The title is required");
            titleInput.addTextChangedListener(new RemoveErrorTextWatcher(titleLayout));
            return false;
        }
        if (TextUtils.isEmpty(hoursInput.getText())) {
            hoursLayout.setError("The hours is required");
            hoursInput.addTextChangedListener(new RemoveErrorTextWatcher(hoursLayout));
            return false;
        }
        if (TextUtils.isEmpty(truancyInput.getText())) {
            truancyLayout.setError("The truancy is required");
            truancyInput.addTextChangedListener(new RemoveErrorTextWatcher(truancyLayout));
            return false;
        }

        if (!validateAllRulesSumHundred()) {
            return false;
        }
        return true;
    }

    private void createCustomRule() {
        if (String.valueOf(nameInput.getText()).equals("") || String.valueOf(percentatgeInput.getText()).equals("")) {
            return;
        }

        if (isEditing) {
            TextView rule = (TextView) views.get(editingRule).getChildAt(0);
            TextView percentage = (TextView) views.get(editingRule).getChildAt(1);
            rule.setText(nameInput.getText());
            percentage.setText(percentatgeInput.getText());
            nameInput.setText("");
            percentatgeInput.setText("");
            isEditing = false;
            return;
        }
        Rule rule = new Rule(String.valueOf(nameInput.getText()), Integer.parseInt(String.valueOf(percentatgeInput.getText())));
        int size = rulesList.size();
        rulesList.add(rule);
        nameInput.setText("");
        percentatgeInput.setText("");

        RelativeLayout newLayout = new RelativeLayout(NewUfActivity.this);

        views.add(newLayout);
        TextView ruleName = new TextView(NewUfActivity.this);
        TextView rulePercentatge = new TextView(NewUfActivity.this);
        Button editButton = new Button(NewUfActivity.this);
        editButtons.add(editButton);
        Button deleteButton = new Button(NewUfActivity.this);
        deleteButtons.add(deleteButton);

        LinearLayoutCompat buttonsLayout = new LinearLayoutCompat(NewUfActivity.this);

        LinearLayoutCompat.LayoutParams dimensions = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
        LinearLayoutCompat.LayoutParams dimensions2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);

        newLayout.setLayoutParams(dimensions);
        ruleName.setLayoutParams(parms);
        rulePercentatge.setLayoutParams(parms);
        buttonsLayout.setLayoutParams(dimensions2);

        ruleName.setText(rule.getTitle());
        rulePercentatge.setText(String.valueOf(rule.getPercentage()));
        rulePercentatge.setGravity(Gravity.CENTER_HORIZONTAL);
        deleteButton.setBackgroundResource(R.drawable.trash);
        editButton.setBackgroundResource(R.drawable.edit);
        deleteButton.setTag(size);
        editButton.setTag(size);
        buttonsLayout.setGravity(Gravity.RIGHT);

        newLayout.addView(ruleName);
        newLayout.addView(rulePercentatge);
        buttonsLayout.addView(editButton);
        buttonsLayout.addView(deleteButton);
        newLayout.addView(buttonsLayout);
        deleteButton.getLayoutParams().width=150;
        deleteButton.getLayoutParams().height=150;
        editButton.getLayoutParams().width=150;
        editButton.getLayoutParams().height=150;

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editingRule = Integer.parseInt(view.getTag().toString());
                Rule rule = rulesList.get(editingRule);
                nameInput.setText(rule.getTitle());
                percentatgeInput.setText(String.valueOf(rule.getPercentage()));
                isEditing = true;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt(view.getTag().toString());
                rulesList.remove(position);
                views.get(position).removeAllViews();
                views.remove(position);
                updateTags(position);
            }
        });

        rulesLayout.addView(newLayout);
    }

    private void updateTags(int position) {
        for ( int i = position; i <= rulesList.size(); i++) {
            deleteButtons.get(i).setTag(Integer.parseInt(deleteButtons.get(i).getTag().toString()) - 1);
            editButtons.get(i).setTag(Integer.parseInt(editButtons.get(i).getTag().toString()) - 1);
        }
    }

    private boolean checkRulesSumDoLessThanAHundred(int newPercentage) {
        for (int i = 0; i < rulesList.size(); i++) {
            newPercentage += rulesList.get(i).getPercentage();
            System.out.println(newPercentage);
        }
        return newPercentage <= 100;
    }

    private boolean validateAllRulesSumHundred() {
        int count = 0;
        for (int i = 0; i < rulesList.size(); i++) {
            count += rulesList.get(i).getPercentage();
        }
        return count == 100;
    }

    public void createOrSaveUfService(JsonObject object, String id, String action){
        Log.d("DAVID", "ENTRA FUNCION 2");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi UfPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);


        Call<JsonObject> call;
        if (action.equals("create")) {
            call = UfPlaceHolderApi.createUf(AuthBearerToken.getAuthBearerToken(getApplicationContext()), object);
        } else {
            call = UfPlaceHolderApi.updateUf(AuthBearerToken.getAuthBearerToken(getApplicationContext()), id, object);
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    System.out.println(jsonObject);
                    if (ufId.equals("")) {
                        ufId = jsonObject.get("body").getAsJsonObject().get("_id").getAsString();
                    }
                    JsonArray rules = new JsonArray();
                    for (int i = 0; i < rulesList.size(); i++) {
                        JsonObject rule = new JsonObject();
                        rule.add("ufId", new JsonParser().parse(ufId));
                        rule.add("title", new JsonParser().parse(rulesList.get(i).getTitle()));
                        rule.addProperty("percentage", Integer.parseInt(String.valueOf(rulesList.get(i).getPercentage())));
                        rules.add(rule);
                    }

                    for (int i = 0; i < rules.size(); i++) {
                        createRule(rules.get(i).getAsJsonObject());
                    }
                    if (action.equals("update")) {
                        for (int i = 0; i < lastRules.size(); i++) {
                            deleteRule(lastRules.get(i).getAsJsonObject());
                        }
                    }
                    ToastError.execute(NewUfActivity.this,"Uf saved successfully");
                    Intent intent = new Intent(NewUfActivity.this, NavigationActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("DAVID", response.toString());
                    ToastError.execute(NewUfActivity.this, response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("DAVID", t.getMessage());
                ToastError.execute(NewUfActivity.this, t.getMessage());
            }
        });
    }


    public void createRule(JsonObject object){

        Log.d("DAVID", "ENTRA CREATE RULE");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RulePlaceHolderApi RulePlaceHolderApi = retrofit.create(RulePlaceHolderApi.class);

        Call<JsonObject> call = RulePlaceHolderApi.createTask(AuthBearerToken.getAuthBearerToken(getApplicationContext()), object);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    ToastError.execute(NewUfActivity.this, response.message());
                } else {
                    ToastError.execute(NewUfActivity.this, response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(NewUfActivity.this, t.getMessage());
            }
        });
    }

    public void deleteRule(JsonObject object){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RulePlaceHolderApi RulePlaceHolderApi = retrofit.create(RulePlaceHolderApi.class);

        Call<JsonObject> call = RulePlaceHolderApi.deleteTask(AuthBearerToken.getAuthBearerToken(getApplicationContext()), object, object.get("id").getAsString());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    //ToastSuccess.execute(NewUfActivity.this, response.message(), NavigationActivity.class);
                    ToastError.execute(NewUfActivity.this, response.message());
                } else {
                    ToastError.execute(NewUfActivity.this, response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(NewUfActivity.this, t.getMessage());
            }
        });
    }

    private void prepareCreateUpdateUfCall() {
        Log.d("DAVID", "ENTRA FUNCION");
        JsonObject uf = new JsonObject();
        uf.add("moduleId", new JsonParser().parse(String.valueOf(moduleId)));
        uf.add("name", new JsonParser().parse(String.valueOf(titleInput.getText())));
        uf.addProperty("hours", Integer.parseInt(String.valueOf(hoursInput.getText())));
        uf.addProperty("truancy_percentage", Integer.parseInt(String.valueOf(truancyInput.getText())));

        createOrSaveUfService(
                uf, action.equals("update") ? ufId : "",
                action
        );
    }

    public void getAllRulesService(String ufId, Context context){
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
                    System.out.println("SUCCESS");
                    ResponseRulesFromUf responseRulesFromUf = response.body();
                    List<Rule> rules = responseRulesFromUf.getBody();
                    for (int i = 0; i < rules.size(); i++) {
                        createSingleRule(rules.get(i));
                    }
                } else {
                    ToastError.execute(context, response.toString());
                    System.out.println("OOOPS");
                }
            }
            @Override
            public void onFailure(Call<ResponseRulesFromUf> call, Throwable t) {
                ToastError.execute(context, t.getMessage());
                System.out.println("FAIL");
            }
        });
    }

    private void createSingleRule(Rule rule) {
        rulesList.add(rule);
        JsonObject lastRule = new JsonObject();
        lastRule.add("id", new JsonParser().parse(rule.getId()));
        lastRule.add("ufId", new JsonParser().parse(ufId));
        lastRule.add("title", new JsonParser().parse(rule.getId()));
        lastRule.addProperty("percentage", rule.getPercentage());
        lastRules.add(lastRule);
        nameInput.setText("");
        percentatgeInput.setText("");

        RelativeLayout newLayout = new RelativeLayout(NewUfActivity.this);

        views.add(newLayout);
        TextView ruleName = new TextView(NewUfActivity.this);
        TextView rulePercentatge = new TextView(NewUfActivity.this);
        Button editButton = new Button(NewUfActivity.this);
        editButtons.add(editButton);
        Button deleteButton = new Button(NewUfActivity.this);
        deleteButtons.add(deleteButton);

        LinearLayoutCompat buttonsLayout = new LinearLayoutCompat(NewUfActivity.this);

        LinearLayoutCompat.LayoutParams dimensions = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
        LinearLayoutCompat.LayoutParams dimensions2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);

        newLayout.setLayoutParams(dimensions);
        ruleName.setLayoutParams(parms);
        rulePercentatge.setLayoutParams(parms);
        buttonsLayout.setLayoutParams(dimensions2);

        ruleName.setText(rule.getTitle());
        rulePercentatge.setText(String.valueOf(rule.getPercentage()));
        rulePercentatge.setGravity(Gravity.CENTER_HORIZONTAL);
        deleteButton.setBackgroundResource(R.drawable.trash);
        editButton.setBackgroundResource(R.drawable.edit);
        deleteButton.setTag(rulesList.size());
        editButton.setTag(rulesList.size());
        buttonsLayout.setGravity(Gravity.RIGHT);

        newLayout.addView(ruleName);
        newLayout.addView(rulePercentatge);
        buttonsLayout.addView(editButton);
        buttonsLayout.addView(deleteButton);
        newLayout.addView(buttonsLayout);
        deleteButton.getLayoutParams().width=150;
        deleteButton.getLayoutParams().height=150;
        editButton.getLayoutParams().width=150;
        editButton.getLayoutParams().height=150;

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editingRule = Integer.parseInt(view.getTag().toString());
                Rule rule = rulesList.get(editingRule);
                nameInput.setText(rule.getTitle());
                percentatgeInput.setText(String.valueOf(rule.getPercentage()));
                isEditing = true;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt(view.getTag().toString());
                rulesList.remove(position);
                views.get(position).removeAllViews();
                views.remove(position);
                updateTags(position);
            }
        });

        rulesLayout.addView(newLayout);
    }
}

