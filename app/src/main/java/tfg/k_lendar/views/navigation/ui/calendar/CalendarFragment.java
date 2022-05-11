package tfg.k_lendar.views.navigation.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tfg.k_lendar.R;
import tfg.k_lendar.databinding.CalendarFragmentBinding;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.http.models.truancy.TruancyRequest;
import tfg.k_lendar.views.shared.TaskTruancyActivity;
import tfg.k_lendar.views.shared.ui.main.fragments.NewTaskFragment;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private CalendarFragmentBinding binding;

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
        return root;






    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                System.out.println("culo");

                Intent intent = new Intent(view.getContext(), TaskTruancyActivity.class);
                intent.putExtra("date", String.valueOf(clickedDayCalendar));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}