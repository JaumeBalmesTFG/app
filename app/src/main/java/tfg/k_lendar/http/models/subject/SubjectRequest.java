package tfg.k_lendar.http.models.subject;

import com.google.gson.annotations.SerializedName;

public class SubjectRequest {

    @SerializedName("name")
    private String name;

    @SerializedName("color")
    private String color;

    public SubjectRequest(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
