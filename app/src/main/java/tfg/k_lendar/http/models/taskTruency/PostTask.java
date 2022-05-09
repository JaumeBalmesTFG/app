package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

public class PostTask {

    @SerializedName("message")
    private String message;

    public PostTask(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
