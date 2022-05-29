package tfg.k_lendar.views.navigation.ui.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.DatePicker;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
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
import tfg.k_lendar.databinding.CalendarFragmentBinding;
import tfg.k_lendar.http.api.services.calendar.CalendarPlaceHolderApi;
import tfg.k_lendar.views.shared.TaskTruancyActivity;
import tfg.k_lendar.views.shared.TodayTaskTruancyActivity;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarFragmentBinding binding;
    private Calendar calendar = Calendar.getInstance();
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private List<Calendar> mCalendarDays = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = CalendarFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCalendar;
        calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mCalendarView = (CalendarView) binding.calendarView;
        getTasksTruanciesFromCalendar();




        binding.calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();String strdate = null;
                SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
                strdate = formatDate.format(clickedDayCalendar.getTime());
                Intent intent = new Intent(getContext(), TodayTaskTruancyActivity.class);
                intent.putExtra("date", strdate);
                getContext().startActivity(intent);
            }
        });

        return root;
    }

    public void getTasksTruanciesFromCalendar(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CalendarPlaceHolderApi calendarPlaceHolderApi = retrofit.create(CalendarPlaceHolderApi.class);


        Call<JsonObject> call = calendarPlaceHolderApi.getTasksTruanciesFromCalendar(AuthBearerToken.getAuthBearerToken(getContext()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject body = response.body();
                    JsonArray array = body.getAsJsonArray("body");
                    for (int i = 0; i < array.size(); i++) {
                        JsonObject row = array.get(i).getAsJsonObject();
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Date date = null;
                        try {
                            date = formatter.parse(row.get("date").getAsString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        mCalendarDays.add(calendar);
                        mEventDays.add(new EventDay(calendar,
                                    R.drawable.klendar_circle, Color.parseColor(row.get("backgroundColor").getAsString())
                            ));
                    }
                    mCalendarView.setEvents(mEventDays);
                } else {
                    ToastError.execute(getContext(), response.toString());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(getContext(), t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}