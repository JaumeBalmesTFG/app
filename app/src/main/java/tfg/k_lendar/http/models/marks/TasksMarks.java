package tfg.k_lendar.http.models.marks;

import com.google.gson.annotations.SerializedName;

import tfg.k_lendar.http.models.rule.Rule;

public class TasksMarks {

    @SerializedName("_id")
    private String id;

    @SerializedName("ruleId")
    private String ruleId;

    @SerializedName("name")
    private String name;

    @SerializedName("grade")
    private Integer grade;

    @SerializedName("rule")
    private Rule rule;

    public TasksMarks(String id, String ruleId, String name, Integer grade, Rule rule) {
        this.id = id;
        this.ruleId = ruleId;
        this.name = name;
        this.grade = grade;
        this.rule = rule;
    }

    public String getId() {
        return id;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public Rule getRule() {
        return rule;
    }
}
