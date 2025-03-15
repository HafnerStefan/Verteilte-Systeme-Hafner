package ch.hftm.blog.dto.responseDTO;

import ch.hftm.blog.dto.UserGraphQL_DTO;
import ch.hftm.blog.entity.User;
import org.eclipse.microprofile.graphql.Type;

@Type("LoginResponse")
public class LoginResponse {

    private boolean success;
    private String token;
    private User user;

    public LoginResponse() {
    }

    public LoginResponse(boolean success, String token, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

