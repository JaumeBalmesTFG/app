package tfg.k_lendar.http.models.task;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Task {

    @SerializedName("message")
    private String message;

    @SerializedName("body")
    private TaskBodyResponse body;

    public Task(String message, TaskBodyResponse body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public TaskBodyResponse getBody() {
        return body;
    }
}
