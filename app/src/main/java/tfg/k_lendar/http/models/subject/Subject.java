package tfg.k_lendar.http.models.subject;

import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("message")
    private String message;

    public Subject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
