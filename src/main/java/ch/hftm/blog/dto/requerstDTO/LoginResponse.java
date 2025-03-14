package ch.hftm.blog.dto.requerstDTO;

import ch.hftm.blog.dto.UserGraphQL_DTO;
import org.eclipse.microprofile.graphql.Type;

@Type("LoginResponse")
public class LoginResponse {

    private boolean success;
    private String token;
    private UserGraphQL_DTO user;

    public LoginResponse() {
    }

    public LoginResponse(boolean success, String token, UserGraphQL_DTO user) {
        this.success = success;
        this.token = token;
        this.user = user;
    }

    // Getter / Setter ...

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserGraphQL_DTO getUser() {
        return user;
    }

    public void setUser(UserGraphQL_DTO user) {
        this.user = user;
    }
}

