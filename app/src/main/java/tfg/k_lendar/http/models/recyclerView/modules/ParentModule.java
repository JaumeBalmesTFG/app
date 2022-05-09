package tfg.k_lendar.http.models.recyclerView.modules;

import java.util.List;
import tfg.k_lendar.http.models.taskTruency.Module;

public class ParentModule extends Module {



    List<ChildUf> childUfs;

    public ParentModule(String id, String name, String color, List<ChildUf> childUfs) {
        super(id, name, color);
        this.childUfs = childUfs;
    }


    public List<ChildUf> getChildUfs() {
        return childUfs;
    }


}
