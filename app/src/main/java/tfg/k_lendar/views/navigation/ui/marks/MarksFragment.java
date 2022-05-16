package tfg.k_lendar.views.navigation.ui.marks;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import tfg.k_lendar.R;
import tfg.k_lendar.databinding.MarksFragmentBinding;


public class MarksFragment extends Fragment {

    private MarksViewModel marksViewModel;
    private MarksFragmentBinding binding;
    CardView subjectCard, ufCard, taskCard;
    Button arrowButton, secondArrowButton;
    LinearProgressIndicator progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        marksViewModel =
                new ViewModelProvider(this).get(MarksViewModel.class);

        binding = MarksFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //final TextView textView = binding.textMarks;
        //marksViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            //@Override
            //public void onChanged(@Nullable String s) {
                //textView.setText(s);
            //}
        //});
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        subjectCard = view.findViewById(R.id.subjectCard);
        ufCard = view.findViewById(R.id.ufCard);
        taskCard = view.findViewById(R.id.taskCard);
        arrowButton = view.findViewById(R.id.arrowButton);
        secondArrowButton = view.findViewById(R.id.secondArrowButton);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setProgress(80);

        arrowButton.setOnClickListener(new View.OnClickListener() {
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
        });

        secondArrowButton.setOnClickListener(new View.OnClickListener() {
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
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}