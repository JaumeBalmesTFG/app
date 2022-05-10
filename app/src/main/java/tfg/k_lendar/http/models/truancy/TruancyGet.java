package tfg.k_lendar.http.models.truancy;

import com.google.gson.annotations.SerializedName;

public class TruancyGet {

    @SerializedName("_id")
    private String _id;

    @SerializedName("ufId")
    private String ufId;

    @SerializedName("date")
    private String date;

    @SerializedName("reason")
    private String reason;

    @SerializedName("hours")
    private int hours;

    public TruancyGet(String _id, String ufId, String date, String reason, int hours) {
        this._id = _id;
        this.ufId = ufId;
        this.date = date;
        this.reason = reason;
        this.hours = hours;
    }

    public String get_id() {
        return _id;
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

    @Override
    public String toString() {
        return "TruancyGet{" +
                "_id='" + _id + '\'' +
                ", ufId='" + ufId + '\'' +
                ", date='" + date + '\'' +
                ", reason='" + reason + '\'' +
                ", hours=" + hours +
                '}';
    }
}
