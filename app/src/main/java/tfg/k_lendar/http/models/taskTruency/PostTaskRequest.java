package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @SerializedName("dueDate")
    private String dueDate;

    public PostTaskRequest(String ufId, String ruleId, String name, int grade, String description, String dueDate) {
        this.ufId = ufId;
        this.ruleId = ruleId;
        this.name = name;
        this.grade = grade;
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

    public int getGrade() {
        return grade;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }
}
