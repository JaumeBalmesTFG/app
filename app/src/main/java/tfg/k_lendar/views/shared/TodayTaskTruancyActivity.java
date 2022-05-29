package tfg.k_lendar.views.shared;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.calendar.CalendarPlaceHolderApi;
import tfg.k_lendar.http.models.helper.TaskTruancySimple;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.views.module.ArchivedModulesActivity;
import tfg.k_lendar.views.navigation.ui.subjects.archived.ListAdapter;

public class TodayTaskTruancyActivity extends AppCompatActivity {

    public static String calendarDate;
    List<TaskTruancySimple> simpleTaskTruancy = new ArrayList<>();
    TodaySimpleAdapter todaySimpleAdapter;
    RecyclerView recyclerView;
    Button createTask, createTruancy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_truancy_today);
        calendarDate = getIntent().getStringExtra("date");
        String formattedDate = getIntent().getStringExtra("dueDate");
        TextView date = findViewById(R.id.dateSelected);
        date.setText(formattedDate);
        recyclerView = findViewById(R.id.recyclerViewTaskTruancy);
        createTask = findViewById(R.id.createNewTask);
        createTruancy = findViewById(R.id.createNewTruancy);

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TaskTruancyActivity.class);
                i.putExtra("frgToLoad", 0);
                startActivity(i);
            }
        });

        createTruancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TaskTruancyActivity.class);
                i.putExtra("frgToLoad", 1);
                startActivity(i);
            }
        });

        getTasksTruanciesFromToday(formattedDate,this.getApplicationContext(), this);

    }

    public void getTasksTruanciesFromToday(String date, Context context, TodayTaskTruancyActivity todayTaskTruancyActivity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CalendarPlaceHolderApi calendarPlaceHolderApi = retrofit.create(CalendarPlaceHolderApi.class);


        Call<JsonObject> call = calendarPlaceHolderApi.getTasksTruanciesFromCalendar(AuthBearerToken.getAuthBearerToken(context));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject body = response.body();
                    JsonArray array = body.getAsJsonArray("body");
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject row = array.get(i).getAsJsonObject();
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                        if (row.get("elementType").getAsString().equals("task")) {
                            System.out.println(row);
                            simpleTaskTruancy.add(
                                    new TaskTruancySimple(
                                            row.get("elementType").getAsString(),
                                            row.get("title").getAsString(),
                                            row.get("moduleId").getAsString(),
                                            row.get("ufId").getAsString(),
                                            row.get("ruleId").getAsString(),
                                            row.get("elementId").getAsString(),
                                            row.get("date").getAsString().
                                            substring(0, row.get("date").getAsString().indexOf("T"))

                                    )
                            );
                        } else {
                            simpleTaskTruancy.add(
                                    new TaskTruancySimple(
                                            row.get("elementType").getAsString(),
                                            row.get("title").getAsString(),
                                            row.get("moduleId").getAsString(),
                                            row.get("ufId").getAsString(),
                                            row.get("elementId").getAsString(),
                                            row.get("date").getAsString().
                                            substring(0, row.get("date").getAsString().indexOf("T"))
                                    )
                            );
                        }
                    }
                todaySimpleAdapter = new TodaySimpleAdapter(simpleTaskTruancy,context, todayTaskTruancyActivity);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(todaySimpleAdapter);
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
}