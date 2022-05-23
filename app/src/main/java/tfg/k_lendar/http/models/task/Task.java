package tfg.k_lendar.http.models.task;

import java.util.Calendar;

public class Task {
    String moduleId;
    String ufId;
    String ruleId;
    String name;
    int grade;
    String description;
    String dueDate;
    boolean done;
    String id;

    @Override
    public String toString() {
        return "Task{" +
                "moduleId='" + moduleId + '\'' +
                ", ufId='" + ufId + '\'' +
                ", ruleId='" + ruleId + '\'' +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", done=" + done +
                ", id='" + id + '\'' +
                '}';
    }

    public Task(String moduleId, String ufId, String ruleId, String name, String description, String dueDate, boolean done, String id) {
        this.moduleId = moduleId;
        this.ufId = ufId;
        this.ruleId = ruleId;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.done = done;
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getUfId() {
        return ufId;
    }

    public void setUfId(String ufId) {
        this.ufId = ufId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}