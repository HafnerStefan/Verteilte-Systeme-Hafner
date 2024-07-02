package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Schema(name = "UserResponse", description = "Base DTO for user")
public class UserBaseDTO {

	@Schema(required = true, example = "32126319")
	private Long id;
	@Schema(required = true, example = "Sandra Dubeli")
	private String name;
	@Schema(required = true, example = "32")
	private int age;
	@Schema(required = true, example = "sandra.dubeli@example.com")
	private String email;
	@Schema(hidden = true, minLength = 8, pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", example = "Password1234")
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

	// Default Constructor
	public UserBaseDTO() {
	}

	// Constructor with all parameters
	public UserBaseDTO(Long id, String name, int age, String email, String password, String address, String phone,
					   String gender, LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
	}

	// Constructor without password
	public UserBaseDTO(Long id, String name, int age, String email, String address, String phone,
					   String gender, LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Constructor without password
	public UserBaseDTO(Long id, String name, int age, String email, String address, String phone,
					   String gender, LocalDate dateOfBirth) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}
}


