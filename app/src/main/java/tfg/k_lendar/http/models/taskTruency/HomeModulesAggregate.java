package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tfg.k_lendar.http.models.recyclerView.modules.ParentModule;

public class HomeModulesAggregate {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private List<ParentModule> body;

    public HomeModulesAggregate(String message, List<ParentModule> body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public List<ParentModule> getBody() {
        return body;
    }
}
