package tfg.k_lendar.views.navigation.ui.subjects;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.ToastSuccess;
import tfg.k_lendar.databinding.NewEditSubjectDialogBinding;
import tfg.k_lendar.databinding.SubjectsFragmentBinding;
import tfg.k_lendar.http.api.services.subject.SubjectPlaceHolderApi;
import tfg.k_lendar.http.api.services.uf.UfPlaceHolderApi;
import tfg.k_lendar.http.models.subject.Subject;
import tfg.k_lendar.http.models.subject.SubjectArchiveRequest;
import tfg.k_lendar.http.models.subject.SubjectRequest;
import tfg.k_lendar.http.models.taskTruency.Modules;
import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.http.models.uf.PostUf;
import tfg.k_lendar.http.models.uf.UfRequest;

public class SubjectsFragment extends Fragment {

    private SubjectsViewModel subjectsViewModel;
    private SubjectsFragmentBinding binding;
    private SubjectsAdapter adapter;
    Button editUf,deleteUf;

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

        editUf = view.findViewById(R.id.button_edit);
        deleteUf = view.findViewById(R.id.button_delete);

        //TODO App peta cuando esto esta descomentado
        /*editUf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO EDIT UF


                editUf(new UfRequest("624cc1ddcb145b0f61a0506b","La Uf2", 100, 20),"624cc86df4af5f69e8226ede");
            }
        });

        deleteUf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO DELETE UF


                deleteUf(new UfRequest("624cc1ddcb145b0f61a0506b","La Uf2", 100, 20),"624cc86df4af5f69e8226ede");
            }
        });*/

        binding.menuFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        //Aqui solo entra si pulsamos el boton de "3 puntos" de arriba, sino, NO EJECUTA lo de Abajo
        binding.addButton.setOnClickListener(add -> {
            Toast.makeText(getContext(), "Add button: " + moduleItemClick.getName(), Toast.LENGTH_SHORT).show();
            //TODO OPEN NEW-UF MENU


            createUf(new UfRequest("{{moduleId}}", "La Uf61", 100, 20));
        });

        binding.editButton.setOnClickListener(add -> {
            Toast.makeText(getContext(), "Edit button: " + moduleItemClick.getName(), Toast.LENGTH_SHORT).show();
            //TODO OPEN EDIT-SUBJECT MENU


            editSubject(new SubjectRequest("SUBJECT ","#33FF9F"),"id");  // TODO Falta cambiar "ID" del SUBJECT para editar
        });

        binding.archiveButton.setOnClickListener(add -> {
            Toast.makeText(getContext(), "Archive button: " + moduleItemClick.getName(), Toast.LENGTH_SHORT).show();
            archiveSubject(new SubjectArchiveRequest(),"id");  // TODO Falta poner "ID" del SUBJECT para archivar
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


    public void editSubject(SubjectRequest subjectRequest, String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.editSubject("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",subjectRequest,id);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    ToastSuccess.execute(getContext(),"Subject edit successful",SubjectsFragment.class);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Subject> call, Throwable t) {}
        });
    }

    public void archiveSubject(SubjectArchiveRequest subjectArchiveRequest, String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.archiveSubject("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",subjectArchiveRequest,id);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    ToastSuccess.execute(getContext(),"Subject archived successful",SubjectsFragment.class);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<Subject> call, Throwable t) {}
        });
    }

    public void createUf(UfRequest ufRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi ufPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);

        Call<PostUf> call = ufPlaceHolderApi.postUf("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",ufRequest);

        call.enqueue(new Callback<PostUf>() {
            @Override
            public void onResponse(Call<PostUf> call, Response<PostUf> response) {
                if (response.isSuccessful()) {
                    PostUf postUf = response.body();
                    ToastSuccess.execute(getContext(),"Create uf successful",SubjectsFragment.class);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<PostUf> call, Throwable t) {}
        });
    }

    public void editUf(UfRequest ufRequest,String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi ufPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);

        Call<PostUf> call = ufPlaceHolderApi.editUf("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",ufRequest,id);

        call.enqueue(new Callback<PostUf>() {
            @Override
            public void onResponse(Call<PostUf> call, Response<PostUf> response) {
                if (response.isSuccessful()) {
                    PostUf postUf = response.body();
                    ToastSuccess.execute(getContext(),"Edit uf successful",SubjectsFragment.class);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<PostUf> call, Throwable t) {}
        });
    }

    public void deleteUf(UfRequest ufRequest,String id){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UfPlaceHolderApi ufPlaceHolderApi = retrofit.create(UfPlaceHolderApi.class);

        Call<PostUf> call = ufPlaceHolderApi.deleteUf("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",ufRequest,id);

        call.enqueue(new Callback<PostUf>() {
            @Override
            public void onResponse(Call<PostUf> call, Response<PostUf> response) {
                if (response.isSuccessful()) {
                    PostUf postUf = response.body();
                    ToastSuccess.execute(getContext(),"Deleted uf successful",SubjectsFragment.class);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<PostUf> call, Throwable t) {}
        });
    }
}