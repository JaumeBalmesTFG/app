package tfg.k_lendar.views.truancy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import tfg.k_lendar.R;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.uf.NewUfActivity;

public class EditTruancyActivity extends AppCompatActivity {

    Button saveButton, removeButton, cancelButton, minusButton, plusButton;
    TextInputEditText hoursInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_truancy);

        saveButton = findViewById(R.id.saveButtonEditTruancy);
        removeButton = findViewById(R.id.removeButtonEditTruancy);
        cancelButton = findViewById(R.id.cancelButtonEditTruancy);
        hoursInput = findViewById(R.id.hoursTextViewEditTruancy);
        minusButton = findViewById(R.id.minusEditTruancy);
        plusButton = findViewById(R.id.plusEditTruancy);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTruancyActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value= hoursInput.getText().toString();
                int finalValue = Integer.parseInt(value);
                String fv = String.valueOf(finalValue);
                if (finalValue<1){
                    finalValue=0;
                    hoursInput.setText(fv);
                } else {
                    finalValue--;
                    fv = String.valueOf(finalValue);
                    hoursInput.setText(fv);
                }

            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = hoursInput.getText().toString();
                int finalValue = Integer.parseInt(value);
                finalValue++;
                String fv = String.valueOf(finalValue);
                hoursInput.setText(fv);
            }
        });


    }
}