package tfg.k_lendar.http.models.uf;

import com.google.gson.annotations.SerializedName;

public class PostUf {


    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private UfRequest body;

    public PostUf(String message, UfRequest body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public UfRequest getBody() {
        return body;
    }


}