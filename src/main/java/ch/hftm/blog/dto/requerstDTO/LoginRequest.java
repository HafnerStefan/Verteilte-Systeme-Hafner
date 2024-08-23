package ch.hftm.blog.dto.requerstDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    // Getter und Setter
    private String email;
    private String password;

}