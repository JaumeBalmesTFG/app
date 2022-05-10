package tfg.k_lendar.http.models.subject;

import com.google.gson.annotations.SerializedName;

public class SubjectBody {

    @SerializedName("authorId")
    private String authorId;

    @SerializedName("name")
    private String name;

    @SerializedName("color")
    private String color;

    @SerializedName("archived")
    private boolean archived;

    @SerializedName("_id")
    private String _id;

    public SubjectBody(String authorId, String name, String color, boolean archived, String _id) {
        this.authorId = authorId;
        this.name = name;
        this.color = color;
        this.archived = archived;
        this._id = _id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean isArchived() {
        return archived;
    }

    public String get_id() {
        return _id;
    }

    @Override
    public String toString() {
        return "SubjectBody{" +
                "authorId='" + authorId + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", archived=" + archived +
                ", _id='" + _id + '\'' +
                '}';
    }
}
