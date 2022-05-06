package tfg.k_lendar.http.models.task;

import com.google.gson.annotations.SerializedName;

public class TaskRequest {

    @SerializedName("authorId")
    private String authorId;

    @SerializedName("ufId")
    private String ufId;

    @SerializedName("name")
    private String name;

    @SerializedName("grade")
    private int grade;

    @SerializedName("description")
    private String description;

    @SerializedName("dueDate")
    private String dueDate;

    public TaskRequest(String authorId, String ufId, String name, int grade, String description, String dueDate) {
        this.authorId = authorId;
        this.ufId = ufId;
        this.name = name;
        this.grade = grade;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getUfId() {
        return ufId;
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
