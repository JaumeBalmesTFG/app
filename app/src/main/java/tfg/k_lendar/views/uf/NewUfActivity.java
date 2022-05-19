package tfg.k_lendar.views.uf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.RemoveErrorTextWatcher;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.shared.TaskTruancyActivity;

public class NewUfActivity extends AppCompatActivity {

    Button saveButton, cancelButton, cancelButtonExams, editButtonExams, cancelButtonActivities, editButtonActivities;
    TextInputEditText titleInput, nameInput, percentatgeInput, hoursInput, truancyInput;
    TextInputLayout titleLayout, hoursLayout, truancyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_uf);

        saveButton = findViewById(R.id.saveButtonUf);
        cancelButton = findViewById(R.id.cancelButtonUf);
        cancelButtonExams = findViewById(R.id.cancelButtonExams);
        editButtonExams = findViewById(R.id.editButtonExams);
        titleLayout = findViewById(R.id.titleLayoutUf);
        titleInput = findViewById(R.id.titleInputUf);
        nameInput = findViewById(R.id.nameInputUf);
        percentatgeInput = findViewById(R.id.percentatgesInputUf);
        hoursLayout = findViewById(R.id.hoursLayoutUf);
        hoursInput = findViewById(R.id.hoursInputUf);
        truancyLayout = findViewById(R.id.truancyLayoutUf);
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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFormAndSaveTask()) {
                    System.out.println("culo");
                }
            }
        });


    }

    private boolean validateFormAndSaveTask() {
        if (TextUtils.isEmpty(titleInput.getText())) {
            titleLayout.setError("The title is required");
            titleInput.addTextChangedListener(new RemoveErrorTextWatcher(titleLayout));
            return false;
        }
        if (TextUtils.isEmpty(hoursInput.getText())) {
            hoursLayout.setError("The hours is required");
            hoursInput.addTextChangedListener(new RemoveErrorTextWatcher(hoursLayout));
            return false;
        }
        if (TextUtils.isEmpty(truancyInput.getText())) {
            truancyLayout.setError("The truancy is required");
            truancyInput.addTextChangedListener(new RemoveErrorTextWatcher(truancyLayout));
            return false;
        }
        return true;
    }

}