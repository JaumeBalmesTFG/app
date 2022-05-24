package tfg.k_lendar.http.models.rule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tfg.k_lendar.http.models.taskTruency.PostTaskRequest;

public class Rule {

        @SerializedName("_id")
        private String id;

        @SerializedName("ufId")
        private String ufId;

        @SerializedName("title")
        private String title;

        @SerializedName("percentage")
        private int percentage;

    public Rule(String title, int percentage) {
        this.title = title;
        this.percentage = percentage;
    }

    public String getId() {
        return id;
    }

    public String getUfId() {
        return ufId;
    }

    public String getTitle() {
        return title;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return title;
    }
}
