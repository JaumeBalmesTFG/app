package tfg.k_lendar.http.models.module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tfg.k_lendar.http.models.taskTruency.Uf;

public class AllModules {

    @SerializedName("_id")
    private String id;

    @SerializedName("authorId")
    private String authorId;

    @SerializedName("name")
    private String name;

    @SerializedName("color")
    private String color;

    @SerializedName("archived")
    private boolean archived;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("__v")
    private int __v;

    @SerializedName("ufs")
    private List<Uf> ufs;

    public AllModules(String id, String authorId, String name, String color, boolean archived, String createdAt, String updatedAt, int __v, List<Uf> ufs) {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.color = color;
        this.archived = archived;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
        this.ufs = ufs;
    }

    public String getId() {
        return id;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public List<Uf> getUfs() {
        return ufs;
    }

    @Override
    public String toString() {
        return "AllModules{" +
                "id='" + id + '\'' +
                ", authorId='" + authorId + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", archived=" + archived +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", __v=" + __v +
                ", ufs=" + ufs +
                '}';
    }
}
