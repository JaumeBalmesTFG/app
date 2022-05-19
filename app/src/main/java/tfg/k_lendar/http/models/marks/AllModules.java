package tfg.k_lendar.http.models.marks;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import tfg.k_lendar.http.models.taskTruency.Modules;

public class AllModules {

    private List<ModulesMarks> listModules;

    public AllModules(List<ModulesMarks> listModules) {
        this.listModules = listModules;
    }

    public List<ModulesMarks> getBody() {
        return listModules;
    }
}
