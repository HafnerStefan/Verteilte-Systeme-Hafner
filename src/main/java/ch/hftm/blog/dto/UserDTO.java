/*
package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Schema(name = "UserResponse", description = "Response payload for creating or updating a user")
public class UserDTO {

    @Schema(required = true, example = "32126319")
    private Long id;
    @Schema(required = true, example = "Sandra Dubeli")
    private String name;
    @Schema(required = true, example = "32")
    private int age;
    @Schema(required = true, example = "sandra.dubeli@example.com")
    private String email;
    @Schema(writeOnly = true,minLength = 8,pattern ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$" , example = "Password1234")
    private String password;
    @Schema(example = "123 Main St, Anytown, AT 12345")
    private String address;
    @Schema(example = "+41 78 965 26 15")
    private String phone;
    @Schema(example = "female")
    private String gender;
    @Schema(example = "1988-12-31")
    private LocalDate dateOfBirth;
    @Schema(example = "2023-01-02T12:00:00")
    private LocalDateTime createdAt;
    @Schema(example = "2023-01-01T12:00:00")
    private LocalDateTime updatedAt;
    @Schema(example = "[12345, 67890, 13579]")
    private List<Long> blogsIds;
    @Schema(example = "[98765, 43210, 24680]")
    private List<Long> commentsIds;


    // Default Constructor
    public UserDTO() {
    }

    // Constructor with minimal parameters
    public UserDTO(Long id, String name, int age, String email, String address, String phone,
            String gender,
            LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    // Constructor without Created and Update Date
    public UserDTO(Long id, String name, int age, String email, String address, String phone,
            String gender,
            LocalDate dateOfBirth, List<Long> blogsIds,
            List<Long> commentsIds) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.blogsIds = blogsIds;
        this.commentsIds = commentsIds;
    }

    // Constructor with all parameters
    public UserDTO(Long id, String name, int age, String email, String password, String address, String phone,
            String gender,
            LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt, List<Long> blogsIds,
            List<Long> commentsIds) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blogsIds = blogsIds;
        this.commentsIds = commentsIds;
    }

}
*/
