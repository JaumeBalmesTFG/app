package tfg.k_lendar.http.models.marks;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tfg.k_lendar.http.models.taskTruency.Uf;
import tfg.k_lendar.http.models.truancy.Truancy;

public class UfMarks {

    @SerializedName("_id")
    private String id;

    @SerializedName("moduleId")
    private String moduleId;

    @SerializedName("name")
    private String name;

    @SerializedName("hours")
    private int hours;

    @SerializedName("truancy_percentage")
    private int truancy_percentage;

    @SerializedName("archived")
    private boolean archived;

    @SerializedName("truancies")
    private List<TruancyMarks> truancies;

    @SerializedName("tasks")
    private List<TasksMarks> tasks;

    @SerializedName("globalUfGrade")
    private String globalUfGrade;

    @SerializedName("totalTruancies")
    private int totalTruancies;

    public UfMarks(String id, String moduleId, String name, int hours, int truancy_percentage, boolean archived, List<TruancyMarks> truancies, List<TasksMarks> tasks, String globalUfGrade, int totalTruancies) {
        this.id = id;
        this.moduleId = moduleId;
        this.name = name;
        this.hours = hours;
        this.truancy_percentage = truancy_percentage;
        this.archived = archived;
        this.truancies = truancies;
        this.tasks = tasks;
        this.globalUfGrade = globalUfGrade;
        this.totalTruancies = totalTruancies;
    }

    public String getId() {
        return id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public int getTruancy_percentage() {
        return truancy_percentage;
    }

    public boolean isArchived() {
        return archived;
    }

    public List<TruancyMarks> getTruancies() {
        return truancies;
    }

    public List<TasksMarks> getTasks() {
        return tasks;
    }

    public String getGlobalUfGrade() {
        return globalUfGrade;
    }

    public int getTotalTruancies() {
        return totalTruancies;
    }
}
