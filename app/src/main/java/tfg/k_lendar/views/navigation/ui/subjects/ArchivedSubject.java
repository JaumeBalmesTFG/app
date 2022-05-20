package tfg.k_lendar.views.navigation.ui.subjects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tfg.k_lendar.R;

public class ArchivedSubject extends AppCompatActivity {

    Button archivedButton;

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

    }
}