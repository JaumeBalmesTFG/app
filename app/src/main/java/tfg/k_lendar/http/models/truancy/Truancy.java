package tfg.k_lendar.http.models.truancy;

import com.google.gson.annotations.SerializedName;

public class Truancy {

    @SerializedName("message")
    private String message;

    public Truancy(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
