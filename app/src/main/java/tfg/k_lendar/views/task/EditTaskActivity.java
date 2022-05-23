package tfg.k_lendar.views.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tfg.k_lendar.R;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

public class EditTaskActivity extends AppCompatActivity {

    Button saveButton, removeButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        saveButton = findViewById(R.id.saveButtonEditTask);
        removeButton = findViewById(R.id.removeButtonEditTask);
        cancelButton = findViewById(R.id.cancelButtonEditTask);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTaskActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

    }
}