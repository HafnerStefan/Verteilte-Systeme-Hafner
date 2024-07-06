package ch.hftm.blog.dto.requerstDTO;

import java.time.LocalDate;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

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

    @Schema(example = "123 Main St, Anytown, AT 12345")
    private String address;

    @Schema(example = "+41 78 965 26 15")
    private String phone;

    @Schema(example = "female")
    private String gender;

    @Schema(type = SchemaType.STRING, format = "date", example = "1988-12-31")
    private LocalDate dateOfBirth;

}
