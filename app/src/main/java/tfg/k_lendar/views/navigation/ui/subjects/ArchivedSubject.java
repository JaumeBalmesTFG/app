package tfg.k_lendar.views.navigation.ui.subjects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import tfg.k_lendar.R;
import tfg.k_lendar.views.navigation.ui.subjects.archived.ListAdapter;

public class ArchivedSubject extends AppCompatActivity {

    Button archivedButton;
    List<ArchivedSubject> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_archived_subject);
        archivedButton = findViewById(R.id.archiveSubjectButton);

        archivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //init();
    }
/**
    public void init() {
        elements = new ArrayList<>();

        ListAdapter listAdapter = new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.)
    }**/
}