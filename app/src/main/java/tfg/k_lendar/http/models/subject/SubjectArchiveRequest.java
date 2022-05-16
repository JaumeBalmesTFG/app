package tfg.k_lendar.http.models.subject;

import com.google.gson.annotations.SerializedName;

public class SubjectArchiveRequest {

    @SerializedName("archived")
    private boolean archived;

    public SubjectArchiveRequest() {
        this.archived = true;
    }

    public boolean isArchived() {
        return archived;
    }
}
