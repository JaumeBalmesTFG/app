package tfg.k_lendar.http.models.taskTruency;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Module {

        @SerializedName("name")
        private String name;

        @SerializedName("color")
        private String color;

        @SerializedName("ufs")
        private List<PostTaskRequest> postTaskRequest;

        @SerializedName("globalModuleGrade")
        private int globalModuleGrade;

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }

        public List<PostTaskRequest> getuf() {
            return postTaskRequest;
        }

        public int getGlobalModuleGrade() {
            return globalModuleGrade;
        }
}
