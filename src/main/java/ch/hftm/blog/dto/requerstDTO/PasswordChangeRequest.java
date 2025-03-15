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

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
