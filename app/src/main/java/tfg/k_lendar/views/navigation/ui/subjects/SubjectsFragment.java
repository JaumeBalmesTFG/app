package tfg.k_lendar.views.navigation.ui.subjects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.databinding.SubjectsFragmentBinding;
import tfg.k_lendar.http.api.services.module.ModulePlaceHolderApi;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.views.module.ArchivedModulesActivity;
import tfg.k_lendar.views.module.NewEditModuleActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

public class SubjectsFragment extends Fragment {

    private SubjectsViewModel subjectsViewModel;
    private SubjectsFragmentBinding binding;
    private SubjectsAdapter adapter;
    FloatingActionButton fab;
    Intent intent;

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
        /*fab = binding.fabButton;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewEditModuleActivity.class);
                startActivity(intent);
            }
        });*/

        binding.archivedSubjectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), ArchivedModulesActivity.class);
                getContext().startActivity(intent);
            }
        });

        binding.createSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), NewEditModuleActivity.class);
                getContext().startActivity(intent);
            }
        });
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
            Intent intent = new Intent(this.getActivity(), NewUfActivity.class);
            intent.putExtra("moduleId", moduleItemClick.getId());
            getContext().startActivity(intent);
        });

        binding.editButton.setOnClickListener(add -> {
            Intent intent = new Intent(this.getActivity(), NewEditModuleActivity.class);
            intent.putExtra("id", moduleItemClick.getId());
            intent.putExtra("title", moduleItemClick.getName());
            intent.putExtra("color", moduleItemClick.getColor());

            getContext().startActivity(intent);

        });

        binding.archiveButton.setOnClickListener(add -> {
            archiveSubject(getContext(), moduleItemClick, adapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void archiveSubject(Context context, Modules module, SubjectsAdapter adapter){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ModulePlaceHolderApi ModulePlaceHolderApi = retrofit.create(ModulePlaceHolderApi.class);
        Call<JsonObject> call = ModulePlaceHolderApi.archiveUnarchiveModule(AuthBearerToken.getAuthBearerToken(context), module.getId());

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ToastError.execute(context,"Module " + module.getName() + " archived successfully");
                        subjectsViewModel.getAllUfsFromModulesService(context);

                    } else {
                        ToastError.execute(context, "An error ocurred, try again later");
                    }
                } else {
                    ToastError.execute(context, "An error ocurred, try again later");
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ToastError.execute(context, "An error ocurred, try again later");
            }
        });
    }

    @Override
    public void onResume() {

        super.onResume();
        subjectsViewModel.getAllUfsFromModulesService(getContext());

    }
}