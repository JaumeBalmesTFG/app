package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PostTaskRequest {

    @SerializedName("ufId")
    private String ufId;

    @SerializedName("ruleId")
    private String ruleId;

    @SerializedName("name")
    private String name;

    @SerializedName("grade")
    private int grade;

    @SerializedName("description")
    private String description;

    @SerializedName("dueData")
    private Date dueData;

    public PostTaskRequest(String ufId, String ruleId, String name, int grade, String description, Date dueData) {
        this.ufId = ufId;
        this.ruleId = ruleId;
        this.name = name;
        this.grade = grade;
        this.description = description;
        this.dueData = dueData;
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

    public int getGrade() {
        return grade;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueData() {
        return dueData;
    }
}
