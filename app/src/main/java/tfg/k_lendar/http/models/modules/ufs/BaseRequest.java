package tfg.k_lendar.http.models.modules.ufs;

import java.util.List;

public class BaseRequest {
    private String message;
    private List<BaseBody> body;

    public BaseRequest(String message, String path, String method, List<BaseBody> body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public List<BaseBody> getBody() {
        return body;
    }
}
