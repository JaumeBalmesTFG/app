package tfg.k_lendar.http.models.auth;

import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("message")
    private String message;

    public Auth(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
