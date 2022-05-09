package tfg.k_lendar.views.navigation.ui.subjects;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.ToastError;
import tfg.k_lendar.core.sharedpreferences.AuthBearerToken;
import tfg.k_lendar.databinding.SubjectsFragmentBinding;
import tfg.k_lendar.http.api.services.taskTruancy.TaskTruancyPlaceHolderApi;
import tfg.k_lendar.http.models.recyclerView.modules.ChildUf;
import tfg.k_lendar.http.models.recyclerView.modules.ParentModule;
import tfg.k_lendar.http.models.taskTruency.HomeModules;
import tfg.k_lendar.http.models.taskTruency.HomeModulesAggregate;
import tfg.k_lendar.http.models.taskTruency.Module;
import tfg.k_lendar.views.navigation.ui.subjects.adapter.ChildUfAdapter;
import tfg.k_lendar.views.navigation.ui.subjects.adapter.ParentModuleAdapter;

public class SubjectsFragment extends Fragment {

    private SubjectsViewModel subjectsViewModel;
    private SubjectsFragmentBinding binding;

    Button points, buttonEdit;
    FloatingActionButton close, add, archive, edit;
    FloatingActionsMenu fabMenu;
    View view;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.subjects_fragment, container, false);


        binding = SubjectsFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getAllUfsFromModulesService();

        /*fabMenu = binding.menuFab;

        buttonEdit = binding.buttonEdit;
        points = binding.buttonPoints;
        close = binding.accionClose;
        add = binding.accionAdd;
        archive = binding.accionArchive;
        edit = binding.accionEdit; */









/*        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.expand();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.collapse();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        return root;
    }

    public void getAllUfsFromModulesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TaskTruancyPlaceHolderApi TaskTruancyPlaceHolderApi = retrofit.create(TaskTruancyPlaceHolderApi.class);

        Call<HomeModulesAggregate> call = TaskTruancyPlaceHolderApi.getAllUfsAggregate(AuthBearerToken.getAuthBearerToken(getContext()));

        call.enqueue(new Callback<HomeModulesAggregate>() {
            @Override
            public void onResponse(Call<HomeModulesAggregate> call, Response<HomeModulesAggregate> response) {
                if (response.isSuccessful()) {
                    HomeModulesAggregate homeModules = response.body();
                    List<ParentModule> modules = homeModules.getBody();
                    System.out.println(modules);
                    System.out.println(modules.get(0).getName());
                    setRecyclerViewData(modules);
                } else {
                    ToastError.execute(getContext(), response.toString());

                }
            }
            @Override
            public void onFailure(Call<HomeModulesAggregate> call, Throwable t) {
                ToastError.execute(getContext(), t.getMessage());
            }
        });
    }

    public void setRecyclerViewData(List<ParentModule> modules) {
        System.out.println(modules);
        recyclerView = view.findViewById(R.id.mainRecyclerView);
        System.out.println(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ParentModuleAdapter(modules));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}