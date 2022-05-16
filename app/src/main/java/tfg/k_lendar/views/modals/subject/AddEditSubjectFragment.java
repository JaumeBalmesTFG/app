package tfg.k_lendar.views.modals.subject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.http.api.services.subject.SubjectPlaceHolderApi;
import tfg.k_lendar.http.models.subject.Subject;
import tfg.k_lendar.http.models.subject.SubjectRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditSubjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button c1,c2,c3,c4,c5,c6,c7,c8;
    String colorSeleccionado;
    TextInputEditText nameInput;
    MaterialButton saveButton;

    public AddEditSubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEditSubject.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditSubjectFragment newInstance(String param1, String param2) {
        AddEditSubjectFragment fragment = new AddEditSubjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        c1.findViewById(R.id.color1);
        c2.findViewById(R.id.color2);
        c3.findViewById(R.id.color3);
        c4.findViewById(R.id.color4);
        c5.findViewById(R.id.color5);
        c6.findViewById(R.id.color6);
        c7.findViewById(R.id.color7);
        c8.findViewById(R.id.color8);
        nameInput.findViewById(R.id.nameInput);
        saveButton.findViewById(R.id.saveButton);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#009688";
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#4CAF4F";
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#F44336";
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#E91E62";
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#00BBD4";
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#2194F3";
            }
        });

        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#4C62E2";
            }
        });

        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorSeleccionado = "#7E57C2";
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("name",nameInput.toString());
                postNewSubject(new SubjectRequest(nameInput.toString(),colorSeleccionado));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_edit_subject_fragment, container, false);
    }

    public void postNewSubject(SubjectRequest subjectRequest){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SubjectPlaceHolderApi subjectPlaceHolderApi = retrofit.create(SubjectPlaceHolderApi.class);

        Call<Subject> call = subjectPlaceHolderApi.createSubject("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImtsZW5kYXJAZ21haWwuY29tIiwiX2lkIjoiNjI3ZDM1ZmIyYzA4MTM4ZmI5Njc4YTg1IiwiaWF0IjoxNjUyMzcyOTg3fQ.MH_hrHkQMFpV9s4RvMDsyi_uK-L3KIlKVWTq9dQ_rPg",subjectRequest);

        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if (response.isSuccessful()) {
                    Subject subject = response.body();
                    Log.d("AQUI",subject.getMessage());
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

}
