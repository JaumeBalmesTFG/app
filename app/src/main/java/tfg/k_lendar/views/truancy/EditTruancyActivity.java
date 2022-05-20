package tfg.k_lendar.views.truancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tfg.k_lendar.R;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

public class EditTruancyActivity extends AppCompatActivity {

    Button saveButton, removeButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_truancy);

        saveButton = findViewById(R.id.saveButtonEditTruancy);
        removeButton = findViewById(R.id.removeButtonEditTruancy);
        cancelButton = findViewById(R.id.cancelButtonEditTruancy);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTruancyActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

    }
}