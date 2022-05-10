package tfg.k_lendar.http.models.truancy;

import com.google.gson.annotations.SerializedName;

public class TruancyRequest {

    @SerializedName("ufId")
    private String ufId;

    @SerializedName("date")
    private String date;

    @SerializedName("reason")
    private String reason;

    @SerializedName("hours")
    private int hours;

    public TruancyRequest(String ufId, String date, String reason, int hours) {
        this.ufId = ufId;
        this.date = date;
        this.reason = reason;
        this.hours = hours;
    }

    public String getUfId() {
        return ufId;
    }

    public String getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public int getHours() {
        return hours;
    }
}

