package tfg.k_lendar.http.models.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModulesResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private List<AllModules> body;

    public ModulesResponse(String message, List<AllModules> body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public List<AllModules> getBody() {
        return body;
    }
}