package tfg.k_lendar.views.truancy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import tfg.k_lendar.R;
import tfg.k_lendar.http.models.taskTruency.Modules;

public class EditTruancyActivity extends AppCompatActivity {

    TextView subjectTextView;
    TextInputLayout menuSubject;
    AutoCompleteTextView subjectDropDown;

    TextView ufTextView;
    TextInputLayout menuUf;
    AutoCompleteTextView ufDropDown;

    TextView hours;
    LinearLayout hoursLayout;
    Button plus;
    Button minus;
    TextView hoursText;

    TextView reasonsTextView;
    TextInputLayout reasonsLayout;
    TextInputEditText reasonsInput;

    Button saveButton, removeButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_truancy);
        menuSubject = findViewById(R.id.menuSubjects);
        subjectDropDown = findViewById(R.id.subjectsDropdown);
        ufTextView = findViewById(R.id.ufTextView);
        menuUf = findViewById(R.id.menuUf);
        ufDropDown = findViewById(R.id.ufDropdown);
        hours = findViewById(R.id.hoursTextView);
        hoursLayout = findViewById(R.id.hoursLayout);
        plus = findViewById(R.id.plusButton);
        minus = findViewById(R.id.minusButton);
        hoursText = findViewById(R.id.hoursText);
        reasonsTextView = findViewById(R.id.reasonTextView);
        reasonsLayout = findViewById(R.id.reasonLayout);
        reasonsInput = findViewById(R.id.reasonInput);
    }
}