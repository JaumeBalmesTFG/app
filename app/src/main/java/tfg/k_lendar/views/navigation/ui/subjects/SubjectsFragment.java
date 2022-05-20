package tfg.k_lendar.views.navigation.ui.subjects;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;
import tfg.k_lendar.databinding.SubjectsFragmentBinding;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.views.module.NewEditModuleActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

public class SubjectsFragment extends Fragment {

    private SubjectsViewModel subjectsViewModel;
    private SubjectsFragmentBinding binding;
    private SubjectsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = SubjectsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subjectsViewModel = new ViewModelProvider(this).get(SubjectsViewModel.class);

        subjectsViewModel.getAllUfsFromModulesService(getContext());
        setUpObservers();
        //Set up RV
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerSubjects.setLayoutManager(layoutManager);


    }

    private void setUpObservers() {
        subjectsViewModel.getList().observe(getViewLifecycleOwner(), modulesList -> {
            adapter = new SubjectsAdapter(modulesList, this::openBottomMenuFloating);
            binding.recyclerSubjects.setAdapter(adapter);
        });
    }

    private void openBottomMenuFloating(Modules moduleItemClick) {
        binding.menuFab.expand();

        //Set up listeners
        binding.closeButton.setOnClickListener(close -> binding.menuFab.collapse());

        binding.addButton.setOnClickListener(add -> {
            Toast.makeText(getContext(), "Add button: " + moduleItemClick.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity(), NewUfActivity.class);
            intent.putExtra("moduleId", moduleItemClick.getId());
            getContext().startActivity(intent);
        });

        binding.editButton.setOnClickListener(add -> {
            Toast.makeText(getContext(), "Edit button: " + moduleItemClick.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity(), NewEditModuleActivity.class);
            intent.putExtra("id", moduleItemClick.getId());
            intent.putExtra("title", moduleItemClick.getName());
            intent.putExtra("color", moduleItemClick.getColor());

            getContext().startActivity(intent);

        });

        binding.archiveButton.setOnClickListener(add -> {
            Toast.makeText(getContext(), "Archive button: " + moduleItemClick.getName(), Toast.LENGTH_SHORT).show();
            //TODO ARCHIVE BUTTON
        });
    }

    private Modules[] getMockList() {
        List<Uf> ufList = new ArrayList<>();
        ufList.add(new Uf("0", "0", "UF1", 2, 2));
        ufList.add(new Uf("1", "01", "UF2", 6, 4));
        ufList.add(new Uf("2", "02", "UF3", 4, 6));

        return new Modules[]{
                new Modules("0", "BD", "GREEN", ufList),
                new Modules("1", "JAVA", "YELLOW", ufList),
                new Modules("2", "POO", "RED", ufList),
                new Modules("3", "SOCKETS", "BLUE", ufList),

        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}