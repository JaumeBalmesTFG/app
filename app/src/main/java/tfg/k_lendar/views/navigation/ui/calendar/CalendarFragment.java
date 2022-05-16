package tfg.k_lendar.views.navigation.ui.calendar;

import android.content.Intent;
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

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import tfg.k_lendar.databinding.CalendarFragmentBinding;
import tfg.k_lendar.views.shared.TaskTruancyActivity;

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

        binding.calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();String strdate = null;
                SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
                strdate = formatDate.format(clickedDayCalendar.getTime());
                Intent intent = new Intent(getContext(), TaskTruancyActivity.class);
                intent.putExtra("date", strdate);
                getContext().startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}