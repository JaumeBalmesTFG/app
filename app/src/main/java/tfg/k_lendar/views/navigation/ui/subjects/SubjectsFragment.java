package tfg.k_lendar.views.navigation.ui.subjects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import tfg.k_lendar.R;
import tfg.k_lendar.databinding.SubjectsFragmentBinding;

public class SubjectsFragment extends Fragment {

    private SubjectsViewModel subjectsViewModel;
    private SubjectsFragmentBinding binding;

    Button points, buttonEdit;
    FloatingActionsMenu fabMenu;
    //FloatingActionButton add, edit, archive;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.subjects_fragment, container, false);
        points = view.findViewById(R.id.buttonPoints);
        fabMenu = view.findViewById(R.id.menuFab);
        buttonEdit = view.findViewById(R.id.buttonEdit);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.performClick();
            }
        });

        subjectsViewModel =
                new ViewModelProvider(this).get(SubjectsViewModel.class);

        binding = SubjectsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSubjects;
        subjectsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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