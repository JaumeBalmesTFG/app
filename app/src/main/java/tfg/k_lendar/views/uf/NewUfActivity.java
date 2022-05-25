package tfg.k_lendar.views.uf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.RemoveErrorTextWatcher;
import tfg.k_lendar.http.models.rule.Rule;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class NewUfActivity extends AppCompatActivity {

    Button saveButton, cancelButton, addRule;
    TextInputEditText titleInput, nameInput, percentatgeInput, hoursInput, truancyInput;
    TextInputLayout titleLayout, hoursLayout, truancyLayout;
    LinearLayoutCompat rulesLayout;
    List<Rule> rulesList = new ArrayList<>();
   List<RelativeLayout> views = new ArrayList<>();
    List<Button> editButtons = new ArrayList<>();
    List<Button> deleteButtons = new ArrayList<>();
    int editingRule;
    boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_uf);

        saveButton = findViewById(R.id.saveButtonUf);
        cancelButton = findViewById(R.id.cancelButtonUf);
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
                createCustomRule();
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

    private void createCustomRule() {
        if (String.valueOf(nameInput.getText()).equals("") || String.valueOf(percentatgeInput.getText()).equals("")) {
            return;
        }

        if (isEditing) {
            TextView rule = (TextView) views.get(editingRule).getChildAt(0);
            TextView percentage = (TextView) views.get(editingRule).getChildAt(1);
            rule.setText(nameInput.getText());
            percentage.setText(percentatgeInput.getText());
            nameInput.setText("");
            percentatgeInput.setText("");
            isEditing = false;
            return;
        }
        Rule rule = new Rule(String.valueOf(nameInput.getText()), Integer.parseInt(String.valueOf(percentatgeInput.getText())));
        int size = rulesList.size();
        rulesList.add(rule);
        nameInput.setText("");
        percentatgeInput.setText("");

        RelativeLayout newLayout = new RelativeLayout(NewUfActivity.this);

        views.add(newLayout);
        TextView ruleName = new TextView(NewUfActivity.this);
        TextView rulePercentatge = new TextView(NewUfActivity.this);
        Button editButton = new Button(NewUfActivity.this);
        editButtons.add(editButton);
        Button deleteButton = new Button(NewUfActivity.this);
        deleteButtons.add(deleteButton);

        LinearLayoutCompat buttonsLayout = new LinearLayoutCompat(NewUfActivity.this);

        LinearLayoutCompat.LayoutParams dimensions = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
        LinearLayoutCompat.LayoutParams dimensions2 = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT);

        newLayout.setLayoutParams(dimensions);
        ruleName.setLayoutParams(parms);
        rulePercentatge.setLayoutParams(parms);
        buttonsLayout.setLayoutParams(dimensions2);

        ruleName.setText(rule.getTitle());
        rulePercentatge.setText(String.valueOf(rule.getPercentage()));
        rulePercentatge.setGravity(Gravity.CENTER_HORIZONTAL);
        deleteButton.setBackgroundResource(R.drawable.trash);
        editButton.setBackgroundResource(R.drawable.edit);
        deleteButton.setTag(size);
        editButton.setTag(size);
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

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editingRule = Integer.parseInt(view.getTag().toString());
                Rule rule = rulesList.get(editingRule);
                nameInput.setText(rule.getTitle());
                percentatgeInput.setText(String.valueOf(rule.getPercentage()));
                isEditing = true;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt(view.getTag().toString());
                rulesList.remove(position);
                views.get(position).removeAllViews();
                views.remove(position);
                updateTags(position);
            }
        });

        rulesLayout.addView(newLayout);
    }

    private void updateTags(int position) {
        for ( int i = position; i <= rulesList.size(); i++) {
            deleteButtons.get(i).setTag(Integer.parseInt(deleteButtons.get(i).getTag().toString()) - 1);
            editButtons.get(i).setTag(Integer.parseInt(editButtons.get(i).getTag().toString()) - 1);
        }
    }
}

