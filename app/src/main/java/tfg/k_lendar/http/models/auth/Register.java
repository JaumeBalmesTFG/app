package tfg.k_lendar.http.models.auth;

import com.google.gson.annotations.SerializedName;

import tfg.k_lendar.http.api.requests.auth.RegisterRequest;

public class Register {

    @SerializedName("body")
    private RegisterRequest registerRequest;

    @SerializedName("token")
    private String token;

    public Register(RegisterRequest registerRequest, String token) {
        this.registerRequest = registerRequest;
        this.token = token;
    }

    public Register(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    public RegisterRequest getRegisterData() {
        return registerRequest;
    }

    public String getToken() {
        return token;
    }
}

