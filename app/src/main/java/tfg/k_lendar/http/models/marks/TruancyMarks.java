package tfg.k_lendar.http.models.marks;

import com.google.gson.annotations.SerializedName;

public class TruancyMarks {

    @SerializedName("_id")
    private String id;

    @SerializedName("hours")
    private int hours;

    public TruancyMarks(String id, int hours) {
        this.id = id;
        this.hours = hours;
    }

    public String getId() {
        return id;
    }

    public int getHours() {
        return hours;
    }
}