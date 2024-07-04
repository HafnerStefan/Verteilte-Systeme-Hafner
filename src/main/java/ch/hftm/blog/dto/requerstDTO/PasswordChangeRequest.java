package ch.hftm.blog.dto.requerstDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordChangeRequest {
	// Getter und Setter
	private String oldPassword;
	private String newPassword;

}