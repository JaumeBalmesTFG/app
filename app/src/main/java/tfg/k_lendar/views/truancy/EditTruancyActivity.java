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
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        menuSubject = view.findViewById(R.id.menuSubjects);
        subjectDropDown = view.findViewById(R.id.subjectsDropdown);
        ufTextView = view.findViewById(R.id.ufTextView);
        menuUf = view.findViewById(R.id.menuUf);
        ufDropDown = view.findViewById(R.id.ufDropdown);
        hours = view.findViewById(R.id.hoursTextView);
        hoursLayout = view.findViewById(R.id.hoursLayout);
        plus = view.findViewById(R.id.plusButton);
        minus = view.findViewById(R.id.minusButton);
        hoursText = view.findViewById(R.id.hoursText);
        reasonsTextView = view.findViewById(R.id.reasonTextView);
        reasonsLayout = view.findViewById(R.id.reasonLayout);
        reasonsInput = view.findViewById(R.id.reasonInput);



        subjectDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Modules) {
                    selectedModule = (Modules) item;
                    ufsContainer.setVisibility(View.VISIBLE);
                    setUfsInDropdown(selectedModule);
                }
            }
        });


    }
}