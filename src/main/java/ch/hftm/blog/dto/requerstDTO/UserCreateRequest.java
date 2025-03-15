package ch.hftm.blog.dto.requerstDTO;

import ch.hftm.blog.boundry.ValidationGroups;

import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

@Schema(name = "UserCreateRequest", description = "Request payload for creating a new user")
@Name("UserCreate")
public class UserCreateRequest extends UserRequest {

	@NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class)
	@Size(min = 8, message = "Password must be at least 8 characters long", groups = { Default.class, ValidationGroups.Create.class })

	@Schema(writeOnly = true, minLength = 8, pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", example = "Password1234")
	@NonNull
	private String password;


	public @NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class) @Size(min = 8, message = "Password must be at least 8 characters long", groups = {Default.class, ValidationGroups.Create.class}) String getPassword() {
		return password;
	}

	public void setPassword(@NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class) @Size(min = 8, message = "Password must be at least 8 characters long", groups = {Default.class, ValidationGroups.Create.class}) String password) {
		this.password = password;
	}
}
