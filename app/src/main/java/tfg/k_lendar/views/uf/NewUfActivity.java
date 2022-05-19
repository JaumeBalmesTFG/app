package tfg.k_lendar.views.uf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import tfg.k_lendar.R;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class NewUfActivity extends AppCompatActivity {

    Button saveButton, cancelButton, cancelButtonExams, editButtonExams, cancelButtonActivities, editButtonActivities;
    TextInputEditText titleInput, nameInput, percentatgeInput, hoursInput, truancyInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_uf);

        saveButton = findViewById(R.id.saveButtonUf);
        cancelButton = findViewById(R.id.cancelButtonUf);
        cancelButtonExams = findViewById(R.id.cancelButtonExams);
        editButtonExams = findViewById(R.id.editButtonExams);
        titleInput = findViewById(R.id.titleInputUf);
        nameInput = findViewById(R.id.nameInputUf);
        percentatgeInput = findViewById(R.id.percentatgesInputUf);
        hoursInput = findViewById(R.id.hoursInputUf);
        truancyInput = findViewById(R.id.truancyInputUf);
        cancelButtonActivities = findViewById(R.id.cancelButtonActivities);
        editButtonActivities = findViewById(R.id.editButtonActivities);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewUfActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
    }


}