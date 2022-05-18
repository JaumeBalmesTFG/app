package tfg.k_lendar.http.api.services.auth;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tfg.k_lendar.http.api.requests.auth.AuthRequest;
import tfg.k_lendar.http.api.requests.auth.LoginRequest;
import tfg.k_lendar.http.api.requests.auth.RegisterRequest;
import tfg.k_lendar.http.models.auth.Auth;
import tfg.k_lendar.http.models.auth.Login;
import tfg.k_lendar.http.models.auth.Register;

public interface AuthPlaceHolderApi {

    @POST("auth")
    Call<Auth> createPost(@Body AuthRequest authRequest);

    @POST("login")
    Call<JsonObject> createPost(@Body LoginRequest loginRequest);

    @POST("register")
    Call<Register> createPost(@Body RegisterRequest registerRequest);

}

