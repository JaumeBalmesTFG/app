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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authButton = findViewById(R.id.nextButton);
        emailInput = findViewById(R.id.emailInput);
        TextView emailNotValid = findViewById(R.id.emailNotValid);

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail(String.valueOf(emailInput.getText()))){
                    emailInput.setError("Email no válido");
                    return;
                }
                showRegisterForm();
                Log.d("email", String.valueOf(emailInput.getText()));

                //emailInput.setFocusable(false);
                //emailInput.setEnabled(false);
                //emailInput.setInputType(InputType.TYPE_NULL);


            /*switch ("login"){
                case "login":
                    showLoginForm();
                    break;
                case "register":
                    showRegisterForm();
                    break;
            }*/
            }

            private void testFunction() {
                Log.d("BUTTON", "ESTA SI FUNCIONA");
            }
        });
    }


    private void showLoginForm() {
        Log.d("ENTRO AL LOGIN","ENTRO AL LOGIN");
        TextView loginLabel = findViewById(R.id.loginSignInText);
        LinearLayoutCompat passwordLayout = findViewById(R.id.passwordContainer);
        TextInputEditText passwordInput = findViewById(R.id.passwordInput);

        loginLabel.setVisibility(View.VISIBLE);
        loginLabel.setText("Login");

        passwordLayout.setVisibility(View.VISIBLE);

        authButton.setOnClickListener(view ->
                Log.d("password", String.valueOf(passwordInput.getText())));
    }

    private void showRegisterForm() {
        Log.d("ENTRO AL REGISTER","ENTRO AL REGISTER");



        LinearLayoutCompat registerLayout = findViewById(R.id.signInContainer);
        TextView registerLabel = findViewById(R.id.loginSignInText);
        TextInputEditText firstNameInput = findViewById(R.id.firstNameInput);
        TextInputEditText lastNameInput = findViewById(R.id.lastNameInput);
        TextInputEditText newPasswordInput = findViewById(R.id.newPasswordInput);



        registerLayout.setVisibility(View.VISIBLE);
        registerLabel.setVisibility(View.VISIBLE);
        registerLabel.setText("Sign in");



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