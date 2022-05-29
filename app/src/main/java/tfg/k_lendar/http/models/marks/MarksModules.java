package tfg.k_lendar.http.models.marks;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarksModules {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("color")
    private String color;

    @SerializedName("ufs")
    private List<UfMarks> ufs;

    @SerializedName("globalModuleGrade")
    private Integer globalModuleGrade;

    public MarksModules(String id, String name, String color, List<UfMarks> ufs, Integer globalModuleGrade) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.ufs = ufs;
        this.globalModuleGrade = globalModuleGrade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<UfMarks> getUfs() {
        return ufs;
    }

    public Integer getGlobalModuleGrade() {
        return globalModuleGrade;
    }

    @Override
    public String toString() {
        return name;
    }
}
