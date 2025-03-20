package ch.hftm.blog.dto.requerstDTO;

import org.eclipse.microprofile.graphql.Name;

@Name("UserPasswordChange")
public class PasswordChangeRequest {
	// Getter und Setter
	private String oldPassword;
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

}
