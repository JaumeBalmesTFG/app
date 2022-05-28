package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

public class PostTaskRequest {

    @SerializedName("moduleId")
    private String moduleId;

    @SerializedName("ufId")
    private String ufId;

    @SerializedName("ruleId")
    private String ruleId;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("dueDate")
    private String dueDate;

    public PostTaskRequest(String moduleId, String ufId, String ruleId, String name, String description, String dueDate) {
        this.moduleId = moduleId;
        this.ufId = ufId;
        this.ruleId = ruleId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getUfId() {
        return ufId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }
}
