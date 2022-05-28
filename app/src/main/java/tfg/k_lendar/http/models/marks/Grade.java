package tfg.k_lendar.http.models.marks;

import com.google.gson.annotations.SerializedName;

public class Grade {

    @SerializedName("$numberDecimal")
    private String numberDecimal;

    public Grade(String numberDecimal) {
        this.numberDecimal = numberDecimal;
    }

    public String getNumberDecimal() {
        return numberDecimal;
    }
}
