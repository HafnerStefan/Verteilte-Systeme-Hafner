package ch.hftm.blog.dto.requerstDTO;

import java.time.LocalDate;

import ch.hftm.blog.boundry.ValidationGroups;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;

@Setter
@Getter
@Schema(name = "UserRequest", description = "Request payload for creating or updating a user")
public class UserRequest {

    // Getter
    @Schema(required = true, example = "Sandra Dubeli")
    private String name;

    @Schema(required = true, example = "32")
    private int age;

    @Schema(required = true, example = "sandra.dubeli@example.com")
    private String email;

    @NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class)
    @Size(min = 8, message = "Password must be at least 8 characters long", groups = { Default.class,
            ValidationGroups.Create.class })
    @Null(message = "Password must be null for update", groups = ValidationGroups.Update.class)
    @Schema(writeOnly = true, minLength = 8, pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", example = "Password1234")
    private String password;

    @Schema(example = "123 Main St, Anytown, AT 12345")
    private String address;

    @Schema(example = "+41 78 965 26 15")
    private String phone;

    @Schema(example = "female")
    private String gender;

    @Schema(example = "1988-12-31")
    private LocalDate dateOfBirth;

}
