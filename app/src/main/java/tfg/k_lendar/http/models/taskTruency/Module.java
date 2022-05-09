package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Module {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("color")
    private String color;

    @SerializedName("ufs")
    private List<Uf> ufs;

    public Module(String id, String name, String color, List<Uf> ufs) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.ufs = ufs;
    }

    public Module(String id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
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

    public List<Uf> getUfs() {
        return ufs;
    }

    @Override
    public String toString() {
        return name;
    }
}
