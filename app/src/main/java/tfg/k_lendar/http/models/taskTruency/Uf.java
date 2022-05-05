package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

public class Uf {

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

    public Uf(String id, String moduleId, String name, int hours, int truancy_percentage) {
        this.id = id;
        this.moduleId = moduleId;
        this.name = name;
        this.hours = hours;
        this.truancy_percentage = truancy_percentage;
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
}
