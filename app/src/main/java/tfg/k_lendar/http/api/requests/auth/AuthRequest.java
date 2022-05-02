package tfg.k_lendar.http.api.requests.auth;

public class AuthRequest {

    private String email;

    public AuthRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
