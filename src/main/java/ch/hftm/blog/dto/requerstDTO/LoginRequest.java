package ch.hftm.blog.dto.requerstDTO;

import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

@Name("LoginRequest")
public class LoginRequest {
    // Getter und Setter
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

