package tfg.k_lendar.http.models.modules.ufs;

public class BaseUf {

    private String _id;
    private String moduleId;
    private String name;
    private int hours;
    private int truancy_percentage;

    public BaseUf(String _id, String moduleId, String name, int hours, int truancy_percentage) {
        this._id = _id;
        this.moduleId = moduleId;
        this.name = name;
        this.hours = hours;
        this.truancy_percentage = truancy_percentage;
    }

    public String get_id() {
        return _id;
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
