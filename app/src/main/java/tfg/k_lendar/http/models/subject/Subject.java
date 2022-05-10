package tfg.k_lendar.http.models.subject;

import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private SubjectBody body;

    public Subject(String message, SubjectBody body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public SubjectBody getBody() {
        return body;
    }
}
