package tfg.k_lendar.http.models.uf;

import com.google.gson.annotations.SerializedName;

public class UfRequest {

    @SerializedName("moduleId")
    private String moduleId;

    @SerializedName("name")
    private String name;

    @SerializedName("hours")
    private int hours;

    @SerializedName("truancy_percentage")
    private int truancy_percentage;

    @SerializedName("_id")
    private String _id;

    public UfRequest(String moduleId, String name, int hours, int truancy_percentage, String _id) {
        this.moduleId = moduleId;
        this.name = name;
        this.hours = hours;
        this.truancy_percentage = truancy_percentage;
        this._id = _id;
    }

    public UfRequest(String moduleId, String name, int hours, int truancy_percentage) {
        this.moduleId = moduleId;
        this.name = name;
        this.hours = hours;
        this.truancy_percentage = truancy_percentage;
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

    public String get_id() {
        return _id;
    }

    @Override
    public String toString() {
        return "UfRequest{" +
                "moduleId='" + moduleId + '\'' +
                ", name='" + name + '\'' +
                ", hours=" + hours +
                ", truancy_percentage=" + truancy_percentage +
                ", _id='" + _id + '\'' +
                '}';
    }
}