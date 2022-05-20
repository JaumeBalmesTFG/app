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
import tfg.k_lendar.http.models.marks.AllModules;
import tfg.k_lendar.http.models.marks.MarksModules;
import tfg.k_lendar.http.models.marks.TasksMarks;
import tfg.k_lendar.http.models.marks.TruancyMarks;
import tfg.k_lendar.http.models.marks.UfMarks;
import tfg.k_lendar.http.models.rule.Rule;

public class MarksFragment extends Fragment {

    private MarksViewModel marksViewModel;
    private MarksFragmentBinding binding;
    private SubjectRecyclerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        marksViewModel = new ViewModelProvider(this).get(MarksViewModel.class);

        binding = MarksFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        marksViewModel = new ViewModelProvider(this).get(MarksViewModel.class);
        marksViewModel.getAllModulesMarksTruanciesTasksGrades(getContext());
        setUpObservers();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerSubjects2.setLayoutManager(layoutManager);
    }

    private void setUpObservers() {
        marksViewModel.getList().observe(getViewLifecycleOwner(), modulesList -> {
            AllModules allModules = new AllModules(getMockList());
            adapter = new SubjectRecyclerAdapter(allModules);
            binding.recyclerSubjects2.setAdapter(adapter);
        });
    }

    private List<MarksModules> getMockList() {
         List<MarksModules> listMarksModules = new ArrayList<>();
         List<UfMarks> listUfs = new ArrayList<>();
         List<TruancyMarks> listTruancies = new ArrayList<>();
         List<TasksMarks> listTasks = new ArrayList<>();

        listTasks.add(new TasksMarks("62867a84efe70164a6fb786d","62867a7aefe70164a6fb7866","Titulo",4,new Rule("62867a7aefe70164a6fb7866","Tasks",90)));
        listTruancies.add(new TruancyMarks("62867a54efe70164a6fb7816",2));
        listTruancies.add(new TruancyMarks("62867a85efe70164a6fb787b",2));
        listUfs.add(new UfMarks("62867a39efe70164a6fb7810","62867a1befe70164a6fb7804","SQL II",100,20,false, listTruancies, listTasks,"NaN",4));
        listMarksModules.add(new MarksModules("62867a1befe70164a6fb7804","SQLII10142","#1ADB61", listUfs,5));

        return listMarksModules;
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

        //CardView subjectCard, ufCard, taskCard;
        //Button arrowButton, secondArrowButton;
        // LinearProgressIndicator progressBar;

/*
        subjectCard = view.findViewById(R.id.subjectCard);
        ufCard = view.findViewById(R.id.ufCard);
        taskCard = view.findViewById(R.id.taskCard);
        arrowButton = view.findViewById(R.id.arrowButton);
        secondArrowButton = view.findViewById(R.id.secondArrowButton);
        progressBar = view.findViewById(R.id.progressBar);
        */

//progressBar.setProgress(80);

        /*arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ufCard.getVisibility()==View.VISIBLE){
                    TransitionManager.beginDelayedTransition(subjectCard, new AutoTransition());
                        ufCard.setVisibility(View.INVISIBLE);
                        taskCard.setVisibility(View.INVISIBLE);
                        arrowButton.setRotation(270);
                }

                else {
                    TransitionManager.beginDelayedTransition(ufCard, new AutoTransition());
                    ufCard.setVisibility(View.VISIBLE);
                    arrowButton.setRotation(-270);

                }
            }
        });*/

        /*secondArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskCard.getVisibility()==View.VISIBLE){
                    TransitionManager.beginDelayedTransition(subjectCard, new AutoTransition());
                    taskCard.setVisibility(View.INVISIBLE);
                    secondArrowButton.setRotation(270);
                }
                else {
                    TransitionManager.beginDelayedTransition(ufCard, new AutoTransition());
                    taskCard.setVisibility(View.VISIBLE);
                    secondArrowButton.setRotation(-270);

                }
            }
        });*/
