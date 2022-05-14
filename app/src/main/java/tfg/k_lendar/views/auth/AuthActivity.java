package tfg.k_lendar.views.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tfg.k_lendar.core.helpers.RemoveErrorTextWatcher;
import tfg.k_lendar.databinding.ActivityAuthBinding;
import tfg.k_lendar.http.api.ApiClient;
import tfg.k_lendar.http.api.requests.auth.AuthRequest;
import tfg.k_lendar.http.api.requests.auth.LoginRequest;
import tfg.k_lendar.http.api.requests.auth.RegisterRequest;
import tfg.k_lendar.http.api.services.auth.AuthPlaceHolderApi;
import tfg.k_lendar.http.models.auth.Auth;
import tfg.k_lendar.http.models.auth.Login;
import tfg.k_lendar.http.models.auth.Register;
import tfg.k_lendar.views.navigation.NavigationActivity;

public class AuthActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.klendar.es/";
    private final String REGISTER = "REGISTER";
    private final String LOGIN = "LOGIN";
    private final String TOKEN = "token";
    Intent intent;
    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = new Intent(this, NavigationActivity.class);

        binding.nextButton.setOnClickListener(view -> {
            if (!validateEmail(String.valueOf(binding.emailInput.getText()))) {
                // Set error text
                binding.emailLayout.setError("Email empty or not valid");
                binding.emailInput.addTextChangedListener(new RemoveErrorTextWatcher(binding.emailLayout));
                return;
            }
            authService(String.valueOf(binding.emailInput.getText()));
        });
    }


    private void showLoginForm() {
        binding.loginSignInText.setText("Login");
        binding.loginSignInText.setVisibility(View.VISIBLE);
        binding.passwordContainer.setVisibility(View.VISIBLE);

        binding.nextButton.setOnClickListener(view -> {
            if (validateForm(LOGIN, binding.passwordInput)) {
                loginService(new LoginRequest(String.valueOf(binding.emailInput.getText()), String.valueOf(binding.passwordInput.getText())));
            }
        });
    }

    private void showRegisterForm() {
        binding.signInContainer.setVisibility(View.VISIBLE);
        binding.loginSignInText.setVisibility(View.VISIBLE);
        binding.loginSignInText.setText("Register");

        binding.nextButton.setOnClickListener(view -> {
            if (validateForm(REGISTER, binding.newPasswordInput)) {
                registerService(new RegisterRequest(
                        String.valueOf(binding.firstNameInput.getText()),
                        String.valueOf(binding.lastNameInput.getText()),
                        String.valueOf(binding.emailInput.getText()),
                        String.valueOf(binding.newPasswordInput.getText()))
                );
            }
        });
    }


    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean validateForm(String action, TextInputEditText password) {
        boolean isCorrect = true;
        if (!validateEmail(String.valueOf(binding.emailInput.getText()))) {
            binding.emailLayout.setError("Email not valid");
            isCorrect = false;
        }
        if (!validatePassword(String.valueOf(password.getText())) && !action.equals(REGISTER)) {
            binding.passwordLayout.setError("Password must have lowercase, uppercase and a number");
            password.addTextChangedListener(new RemoveErrorTextWatcher(binding.passwordLayout));
            isCorrect = false;
        }
        if (!action.equals(REGISTER)) {
            return isCorrect;
        }
        if (TextUtils.isEmpty(binding.firstNameInput.getText())) {
            binding.firstNameLayout.setError("First name not valid");
            binding.firstNameInput.addTextChangedListener(new RemoveErrorTextWatcher(binding.firstNameLayout));
            isCorrect = false;
        }
        if (TextUtils.isEmpty(binding.lastNameInput.getText())) {
            binding.lastNameLayout.setError("Last name not valid");
            binding.lastNameInput.addTextChangedListener(new RemoveErrorTextWatcher(binding.lastNameLayout));
            isCorrect = false;
        }
        if (!validatePassword(String.valueOf(binding.newPasswordInput.getText()))) {
            binding.newPasswordLayout.setError("Password must have lowercase, uppercase and a number");
            binding.newPasswordInput.addTextChangedListener(new RemoveErrorTextWatcher(binding.newPasswordLayout));
            isCorrect = false;
        }
        return isCorrect;
    }

    private boolean validatePassword(String password) {
        //Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).{4,}$");
        return !password.equals("");
    }

    public void authService(String email) {
        AuthPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(AuthPlaceHolderApi.class);
        AuthRequest authRequest = new AuthRequest(email);

        api.createPost(authRequest).enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response.isSuccessful()) {
                    Auth auth = response.body();
                    switch (auth.getMessage()) {
                        case LOGIN:
                            showLoginForm();
                            break;
                        case REGISTER:
                            showRegisterForm();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
            }
        });
    }

    public void loginService(LoginRequest loginRequest) {
        AuthPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(AuthPlaceHolderApi.class);

        api.createPost(loginRequest).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    Login login = response.body();
                    saveTokenOnSharedPreferences(login.getBody().get(TOKEN));
                    startActivity(intent);
                } else {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
            }
        });
    }

    public void registerService(RegisterRequest registerRequest) {
        AuthPlaceHolderApi api = ApiClient.getClient(BASE_URL).create(AuthPlaceHolderApi.class);

        api.createPost(registerRequest).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful()) {
                    Register register = response.body();
                    saveTokenOnSharedPreferences(register.getToken());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
            }
        });
    }

    public void saveTokenOnSharedPreferences(String token) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }
}