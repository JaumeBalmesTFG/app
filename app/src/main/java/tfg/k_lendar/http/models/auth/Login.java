package tfg.k_lendar.http.models.auth;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Login {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private HashMap<String,String> body;

    public HashMap<String, String> getBody() {
        return body;
    }
}
