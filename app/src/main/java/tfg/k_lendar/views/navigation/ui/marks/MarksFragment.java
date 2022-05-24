package tfg.k_lendar.views.navigation.ui.marks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import tfg.k_lendar.databinding.MarksFragmentBinding;
import tfg.k_lendar.databinding.SubjectsFragmentBinding;
import tfg.k_lendar.http.models.marks.MarksModules;
import tfg.k_lendar.http.models.marks.TasksMarks;
import tfg.k_lendar.http.models.marks.TruancyMarks;
import tfg.k_lendar.http.models.marks.UfMarks;
import tfg.k_lendar.http.models.rule.Rule;

public class MarksFragment extends Fragment {

    private MarksViewModel marksViewModel;
    private MarksFragmentBinding binding;
    private MarksAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MarksFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        marksViewModel = new ViewModelProvider(this).get(MarksViewModel.class);
        //marksViewModel.getAllModulesMarksTruanciesTasksGrades(getContext());
        marksViewModel.setList(getMockList());
        setUpObservers();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerSubjects2.setLayoutManager(layoutManager);
    }

    private void setUpObservers() {
        marksViewModel.getList().observe(getViewLifecycleOwner(), modulesList -> {
            adapter = new MarksAdapter(modulesList);
            binding.recyclerSubjects2.setAdapter(adapter);
        });
    }

    private List<MarksModules> getMockList() {
        List<MarksModules> modules = new ArrayList<>();
        List<UfMarks> ufs = new ArrayList<>();
        List<TruancyMarks> truancies = new ArrayList<>();
        List<TasksMarks> tasks = new ArrayList<>();

        tasks.add(new TasksMarks("62867a84efe70164a6fb786d", "62867a7aefe70164a6fb7866", "Titulo 1", null, new Rule("62867a7aefe70164a6fb7866", "Tasks", 90)));
        tasks.add(new TasksMarks("62867a84efe70164a6fb786d", "62867a7aefe70164a6fb7866", "Titulo 2", null, new Rule("62867a7aefe70164a6fb7866", "Tasks", 90)));

        truancies.add(new TruancyMarks("62867a54efe70164a6fb7816", 2));

        ufs.add(new UfMarks("62867a39efe70164a6fb7810", "62867a1befe70164a6fb7804", "SQL II", 100, 20, false, truancies, tasks, "NAN", 4));

        modules.add(new MarksModules("62867a1befe70164a6fb7804", "GAVNO", "#1ADB61", ufs, null));
        modules.add(new MarksModules("62867a1befe70164a6fb7804", "KAKASHKI", "#1ADB61", ufs, null));
        modules.add(new MarksModules("62867a1befe70164a6fb7804", "RADOSTb", "#1ADB61", ufs, null));

        return modules;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}