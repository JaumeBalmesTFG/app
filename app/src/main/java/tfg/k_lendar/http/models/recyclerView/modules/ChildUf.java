package tfg.k_lendar.http.models.recyclerView.modules;

import com.google.gson.annotations.SerializedName;

import tfg.k_lendar.http.models.taskTruency.Uf;

public class ChildUf extends Uf {

    public ChildUf(String id, String moduleId, String name, int hours, int truancy_percentage) {
        super(id, moduleId, name, hours, truancy_percentage);
    }
}
