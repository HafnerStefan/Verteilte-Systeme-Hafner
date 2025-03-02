package ch.hftm.blog.dto;

import ch.hftm.blog.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


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
	@Schema(type = SchemaType.STRING, format = "date", example = "1988-12-31")
	private LocalDate dateOfBirth;
	@Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
	private LocalDateTime createdAt;
	@Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
	private LocalDateTime updatedAt;
	@JsonIgnore
	private Set<Role> roles;

	// Default Constructor
	public UserBaseDTO() {
	}

	// Constructor with all parameters
	public UserBaseDTO(Long id, String name, int age, String email, String password, String address, String phone,
					   String gender, LocalDate dateOfBirth, Set<Role> roles, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
		this.roles = roles;
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

	// Getter and Setter

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonIgnore
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}


