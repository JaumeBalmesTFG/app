package tfg.k_lendar.http.models.truancy;

import com.google.gson.annotations.SerializedName;

public class TruancyGetRecieve {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private TruancyGet body;

    public TruancyGetRecieve(String message, TruancyGet body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public TruancyGet getBody() {
        return body;
    }
}
