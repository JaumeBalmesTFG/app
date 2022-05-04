package tfg.k_lendar.http.models.modules.ufs;

import java.util.List;

public class BaseBody {

    private String _id;
    private String name;
    private String color;
    private List<BaseUf> ufs;

    public BaseBody(String _id, String name, String color, List<BaseUf> ufs) {
        this._id = _id;
        this.name = name;
        this.color = color;
        this.ufs = ufs;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<BaseUf> getUfs() {
        return ufs;
    }
}
