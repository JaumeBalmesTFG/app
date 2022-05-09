package tfg.k_lendar.http.models.rule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRulesFromUf {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private List<Rule> body;

    public ResponseRulesFromUf(String message, List<Rule> body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public List<Rule> getBody() {
        return body;
    }
}
