package tfg.k_lendar.views.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import tfg.k_lendar.R;
import tfg.k_lendar.http.api.services.auth.AuthService;

public class AuthActivity extends AppCompatActivity {

    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authService = new AuthService();
        setContentView(R.layout.activity_auth);
        authService.execute("klendar@gmail.com");
    }
}