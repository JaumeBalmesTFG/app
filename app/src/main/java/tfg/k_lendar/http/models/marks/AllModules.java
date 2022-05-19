package tfg.k_lendar.http.models.marks;

import java.util.List;

public class AllModules {

    private List<MarksModules> listModules;

    public AllModules(List<MarksModules> listModules) {
        this.listModules = listModules;
    }

    public List<MarksModules> getBody() {
        return listModules;
    }
}
