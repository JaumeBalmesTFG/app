package tfg.k_lendar.views.uf;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.RemoveErrorTextWatcher;
import tfg.k_lendar.http.models.rule.Rule;
import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;
import tfg.k_lendar.views.navigation.NavigationActivity;
import tfg.k_lendar.views.shared.TaskTruancyActivity;

public class NewUfActivity extends AppCompatActivity {

    Button saveButton, cancelButton, cancelButtonExams, editButtonExams, cancelButtonActivities, editButtonActivities, addRule;
    TextInputEditText titleInput, nameInput, percentatgeInput, hoursInput, truancyInput;
    TextInputLayout titleLayout, hoursLayout, truancyLayout;
    LinearLayoutCompat rulesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_uf);

        saveButton = findViewById(R.id.saveButtonUf);
        cancelButton = findViewById(R.id.cancelButtonUf);
        /*cancelButtonExams = findViewById(R.id.cancelButtonExams);
        editButtonExams = findViewById(R.id.editButtonExams);*/
        titleLayout = findViewById(R.id.titleLayoutUf);
        titleInput = findViewById(R.id.titleInputUf);
        nameInput = findViewById(R.id.nameInputUf);
        percentatgeInput = findViewById(R.id.percentatgesInputUf);
        hoursLayout = findViewById(R.id.hoursLayoutUf);
        hoursInput = findViewById(R.id.hoursInputUf);
        truancyLayout = findViewById(R.id.truancyLayoutUf);
        truancyInput = findViewById(R.id.truancyInputUf);
        addRule = findViewById(R.id.addRule);
        rulesLayout = findViewById(R.id.rulesLayout);


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

                }
            }
        });

        addRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("xulo", String.valueOf(nameInput.getText()));
                Log.d("xulo", String.valueOf(percentatgeInput.getText()));
                nameInput.setText("");
                percentatgeInput.setText("");

                RelativeLayout newLayout = new RelativeLayout(NewUfActivity.this);
                TextView ruleName = new TextView(NewUfActivity.this);
                TextView rulePercentatge = new TextView(NewUfActivity.this);
                Button editButton = new Button(NewUfActivity.this);
                Button deleteButton = new Button(NewUfActivity.this);
                LinearLayoutCompat buttonsLayout = new LinearLayoutCompat(NewUfActivity.this);

                LinearLayoutCompat.LayoutParams dimensions = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
                LinearLayoutCompat.LayoutParams dimensions2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);

                newLayout.setLayoutParams(dimensions);
                ruleName.setLayoutParams(parms);
                rulePercentatge.setLayoutParams(parms);
                buttonsLayout.setLayoutParams(dimensions2);




                ruleName.setText("name");
                rulePercentatge.setText("percentatge");
                rulePercentatge.setGravity(Gravity.CENTER_HORIZONTAL);
                deleteButton.setBackgroundResource(R.drawable.trash);
                editButton.setBackgroundResource(R.drawable.edit);
                buttonsLayout.setGravity(Gravity.RIGHT);

                newLayout.addView(ruleName);
                newLayout.addView(rulePercentatge);
                buttonsLayout.addView(editButton);
                buttonsLayout.addView(deleteButton);
                newLayout.addView(buttonsLayout);
                deleteButton.getLayoutParams().width=150;
                deleteButton.getLayoutParams().height=150;
                editButton.getLayoutParams().width=150;
                editButton.getLayoutParams().height=150;


                rulesLayout.addView(newLayout);



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