package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeModules {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private List<Module> body;

    public HomeModules(String message, List<Module> body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public List<Module> getBody() {
        return body;
    }
}
