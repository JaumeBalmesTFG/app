package tfg.k_lendar.views.shared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.http.api.services.calendar.CalendarPlaceHolderApi;

public class TodayTaskTruancyActivity extends AppCompatActivity {

    String calendarDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_truancy_today);
        calendarDate = getIntent().getStringExtra("date");
        System.out.println("FECHA: " + calendarDate);
        getTasksTruanciesFromToday(calendarDate,this.getApplicationContext());

    }

    public void getTasksTruanciesFromToday(String date, Context context){
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
                        System.out.println(row);
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = null;

                        try {
                            date = formatter.parse(row.get("date").getAsString());
                            System.out.println(date);
                            System.out.println(date.equals(calendarDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
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