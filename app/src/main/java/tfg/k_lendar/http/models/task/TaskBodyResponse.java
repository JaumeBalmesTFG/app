package tfg.k_lendar.http.models.task;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskBodyResponse {


    @SerializedName("_id")
    private String _id;

    @SerializedName("authorId")
    private String authorId;

    @SerializedName("ufId")
    private String ufId;

    @SerializedName("name")
    private String name;

    @SerializedName("grade")
    private List<String> grade;

    @SerializedName("description")
    private String description;

    @SerializedName("dueDate")
    private String dueDate;

    @SerializedName("done")
    private boolean done;

    public TaskBodyResponse(String _id, String authorId, String ufId, String name, List<String> grade, String description, String dueDate, boolean done) {
        this._id = _id;
        this.authorId = authorId;
        this.ufId = ufId;
        this.name = name;
        this.grade = grade;
        this.description = description;
        this.dueDate = dueDate;
        this.done = done;
    }

    public String get_id() {
        return _id;
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

    public List<String> getGrade() {
        return grade;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "TaskBodyResponse{" +
                "_id='" + _id + '\'' +
                ", authorId='" + authorId + '\'' +
                ", ufId='" + ufId + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", done=" + done +
                '}';
    }
}
