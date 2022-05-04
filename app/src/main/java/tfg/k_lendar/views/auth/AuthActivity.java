package tfg.k_lendar.views.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tfg.k_lendar.R;
import tfg.k_lendar.core.helpers.RemoveErrorTextWatcher;
import tfg.k_lendar.http.api.requests.auth.AuthRequest;
import tfg.k_lendar.http.api.requests.auth.LoginRequest;
import tfg.k_lendar.http.api.requests.auth.RegisterRequest;
import tfg.k_lendar.http.api.services.auth.AuthPlaceHolderApi;
import tfg.k_lendar.http.models.auth.Auth;
import tfg.k_lendar.http.models.auth.Login;
import tfg.k_lendar.http.models.auth.Register;

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
                    emailInput.addTextChangedListener(new RemoveErrorTextWatcher(emailLayout));
                    return;
                }
                authService(String.valueOf(emailInput.getText()));
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

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm("LOGIN", passwordInput)){
                    loginService(new LoginRequest(String.valueOf(emailInput.getText()), String.valueOf(passwordInput.getText())));
                }
            }
        });
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
        actionTextLabel.setText("Register");

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm("REGISTER", newPasswordInput)) {
                    registerService(new RegisterRequest(
                        String.valueOf(firstNameInput.getText()),
                        String.valueOf(lastNameInput.getText()),
                        String.valueOf(emailInput.getText()),
                        String.valueOf(newPasswordInput.getText()))
                    );
                }
                Log.d("firstName", String.valueOf(firstNameInput.getText()));
                Log.d("lastName",String.valueOf(lastNameInput.getText()));
                Log.d("newPassword",String.valueOf(newPasswordInput.getText()));
            }
        });
    }


    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private boolean validateForm(String action, TextInputEditText password){
        boolean isCorrect = true;
        if (!validateEmail(String.valueOf(emailInput.getText()))){
            emailLayout.setError("Email not valid");
            isCorrect = false;
        }
        if (!validatePassword(String.valueOf(password.getText())) && !action.equals("REGISTER")){
            passwordLayout.setError("Password must have lowercase, uppercase and a number");
            password.addTextChangedListener(new RemoveErrorTextWatcher(passwordLayout));
            isCorrect = false;
        }
        if (!action.equals("REGISTER")) {
            return isCorrect;
        }
        if (TextUtils.isEmpty(firstNameInput.getText())){
            firstNameLayout.setError("First name not valid");
            firstNameInput.addTextChangedListener(new RemoveErrorTextWatcher(firstNameLayout));
            isCorrect = false;
        }
        if (TextUtils.isEmpty(lastNameInput.getText())){
            lastNameLayout.setError("Last name not valid");
            lastNameInput.addTextChangedListener(new RemoveErrorTextWatcher(lastNameLayout));
            isCorrect = false;
        }
        if (!validatePassword(String.valueOf(newPasswordInput.getText()))){
            newPasswordLayout.setError("Password must have lowercase, uppercase and a number");
            newPasswordInput.addTextChangedListener(new RemoveErrorTextWatcher(newPasswordLayout));
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

    public void authService(String email){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthPlaceHolderApi authPlaceHolderApi = retrofit.create(AuthPlaceHolderApi.class);

        AuthRequest authRequest = new AuthRequest(email);

        Call<Auth> call = authPlaceHolderApi.createPost(authRequest);

        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response.isSuccessful()) {
                    Auth auth = response.body();
                    switch (auth.getMessage()){
                        case "LOGIN":
                            showLoginForm();
                            break;
                        case "REGISTER":
                            showRegisterForm();
                            break;
                    }
                }

            }
            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }
        });
    }

    public void loginService(LoginRequest loginRequest){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthPlaceHolderApi authPlaceHolderApi = retrofit.create(AuthPlaceHolderApi.class);
        Call<Login> call = authPlaceHolderApi.createPost(loginRequest);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login login = response.body();
                System.out.println(login.getBody().get("token"));
                //saveTokenOnSharedPreferences(login.getBody());
                System.out.println(response);

                //TODO If response.code() === 406 show password error
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }
        });
    }

    public void registerService(RegisterRequest registerRequest){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.klendar.es/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthPlaceHolderApi authPlaceHolderApi = retrofit.create(AuthPlaceHolderApi.class);

        Call<Register> call = authPlaceHolderApi.createPost(registerRequest);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                //TODO SHOW ERROR HERE
               /* if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }*/

                if (response.isSuccessful()) {
                    Register register = response.body();
                    saveTokenOnSharedPreferences(register.getToken());
                }
            }
            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                //textViewResult.setText(t.getMessage());
            }
        });
    }

    public void saveTokenOnSharedPreferences(String token){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }
}