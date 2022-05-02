package tfg.k_lendar.views.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import tfg.k_lendar.R;

public class AuthActivity extends AppCompatActivity {

    MaterialButton authButton;
    TextInputEditText emailInput;
    TextInputEditText firstNameInput;
    TextInputEditText lastNameInput;
    TextInputEditText newPasswordInput;
    TextView actionTextLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authButton = findViewById(R.id.nextButton);
        emailInput = findViewById(R.id.emailInput);

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail(String.valueOf(emailInput.getText()))){
                    emailInput.setError("Email empty or not valid");
                    return;
                }
                showRegisterForm();
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
        TextView actionTextLabel = findViewById(R.id.loginSignInText);
        LinearLayoutCompat passwordLayout = findViewById(R.id.passwordContainer);
        TextInputEditText passwordInput = findViewById(R.id.passwordInput);

        actionTextLabel.setVisibility(View.VISIBLE);
        actionTextLabel.setText("Login");

        passwordLayout.setVisibility(View.VISIBLE);

        authButton.setOnClickListener(view ->
                //Send email and password to function
                Log.d("password", String.valueOf(passwordInput.getText())));
    }

    private void showRegisterForm() {
        LinearLayoutCompat registerLayout = findViewById(R.id.signInContainer);
        actionTextLabel = findViewById(R.id.loginSignInText);
        TextInputEditText firstNameInput = findViewById(R.id.firstNameInput);
        TextInputEditText lastNameInput = findViewById(R.id.lastNameInput);
        TextInputEditText newPasswordInput = findViewById(R.id.newPasswordInput);

        registerLayout.setVisibility(View.VISIBLE);
        actionTextLabel.setVisibility(View.VISIBLE);
        actionTextLabel.setText("Sign in");

        authButton.setOnClickListener(view ->
                Log.d("firstName", String.valueOf(firstNameInput.getText())));
                Log.d("lastName",String.valueOf(lastNameInput.getText()));
                Log.d("newPassword",String.valueOf(newPasswordInput.getText()));
    }


    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private boolean validateForm(String action){
        if (!validateEmail(String.valueOf(emailInput.getText()))){
            emailInput.setError("Email not valid");
            return false;
        }
        if (!validatePassword(String.valueOf(newPasswordInput.getText()))){
            newPasswordInput.setError("Password not valid");
            return false;
        }
        if (!validateInput(String.valueOf(firstNameInput.getText()))){
            firstNameInput.setError("First name not valid");
            return false;
        }
        if (!validateInput(String.valueOf(lastNameInput.getText()))){
            lastNameInput.setError("Last name not valid");
            return false;
        }
        return true;
    }
    private boolean validatePassword(String password) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(password).matches();
    }
    private boolean validateInput(String input) {
        return !input.equals("");
    }
}