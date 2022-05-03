package tfg.k_lendar.views.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import tfg.k_lendar.R;

public class AuthActivity extends AppCompatActivity {

    MaterialButton authButton;
    TextInputEditText emailInput;
    TextInputEditText firstNameInput;
    TextInputEditText lastNameInput;
    TextInputEditText passwordInput;
    TextInputEditText newPasswordInput;
    TextInputLayout emailLayout;
    TextInputLayout firstNameLayout;
    TextInputLayout lastNameLayout;
    TextInputLayout passwordLayout;
    TextInputLayout newPasswordLayout;
    LinearLayoutCompat passwordContainer;
    TextView actionTextLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        actionTextLabel = findViewById(R.id.loginSignInText);
        authButton = findViewById(R.id.nextButton);
        emailInput = findViewById(R.id.emailInput);
        TextInputLayout emailLayout = findViewById(R.id.emailLayout);

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail(String.valueOf(emailInput.getText()))){
                    // Set error text
                    emailLayout.setError("Email empty or not valid");
                    //emailInput.setError("Email empty or not valid");
                    return;
                }
                showLoginForm();
                //Send to auth function
                Log.d("email", String.valueOf(emailInput.getText()));


            /*switch ("LOGIN"){
                case "login":
                    showLoginForm();
                    break;
                case "REGISTER":
                    showRegisterForm();
                    break;
            }*/
            }
        });
    }


    private void showLoginForm() {
        passwordContainer = findViewById(R.id.passwordContainer);
        passwordLayout = findViewById(R.id.passwordLayout);
        passwordInput = findViewById(R.id.passwordInput);
        actionTextLabel.setText("Login");
        actionTextLabel.setVisibility(View.VISIBLE);
        passwordContainer.setVisibility(View.VISIBLE);

        authButton.setOnClickListener(view ->
                //Send email and password to function
                validateForm("LOGIN", passwordInput));
                Log.d("password", String.valueOf(passwordInput.getText()));
    }

    private void showRegisterForm() {
        LinearLayoutCompat registerLayout = findViewById(R.id.signInContainer);

        firstNameInput = findViewById(R.id.firstNameInput);
        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameInput = findViewById(R.id.lastNameInput);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        newPasswordLayout = findViewById(R.id.newPasswordLayout);

        registerLayout.setVisibility(View.VISIBLE);
        actionTextLabel.setVisibility(View.VISIBLE);
        actionTextLabel.setText("Sign in");

        authButton.setOnClickListener(view ->

                validateForm("REGISTER", newPasswordInput));
                Log.d("firstName", String.valueOf(firstNameInput.getText()));
                Log.d("lastName",String.valueOf(lastNameInput.getText()));
                Log.d("newPassword",String.valueOf(newPasswordInput.getText()));
    }


    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private boolean validateForm(String action, TextInputEditText password){
        boolean isCorrect = true;
        if (!validateEmail(String.valueOf(emailInput.getText()))){
            emailLayout.setError("Email not valid");
            setTextWatcher(emailInput, emailLayout);
            isCorrect = false;
        }
        if (!validatePassword(String.valueOf(password.getText()))){
            passwordLayout.setError("Password must have lowercase, uppercase and a number");
            isCorrect = false;
        }
        if (!action.equals("REGISTER")) {
            return isCorrect;
        }
        if (TextUtils.isEmpty(firstNameInput.getText())){
            firstNameLayout.setError("First name not valid");
            isCorrect = false;
        }
        if (TextUtils.isEmpty(lastNameInput.getText())){
            lastNameLayout.setError("Last name not valid");
            isCorrect = false;
        }
        if (!validatePassword(String.valueOf(newPasswordInput.getText()))){
            newPasswordLayout.setError("Password must have lowercase, uppercase and a number");
            isCorrect = false;
        }
        return isCorrect;
    }
    private boolean validatePassword(String password) {
        //Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{4,}$");
        return !password.equals("");
    }
    private boolean validateInput(String input) {
        return !input.equals("");
    }

    private void setTextWatcher(TextInputEditText textInputEditText, TextInputLayout textInputLayout) {
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence cs, int s, int b, int c) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }
}