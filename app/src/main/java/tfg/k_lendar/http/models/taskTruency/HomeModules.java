package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeModules {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private List<Modules> body;

    public HomeModules(String message, List<Modules> body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public List<Modules> getBody() {
        return body;
    }
}
