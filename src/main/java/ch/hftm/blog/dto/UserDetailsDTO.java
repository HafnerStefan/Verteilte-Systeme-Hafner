package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Schema(name = "UserDetailsResponse", description = "DTO for detailed user view with blogs and comments")
public class UserDetailsDTO extends UserBaseDTO {

	@Schema(description = "List of blog details")
	private List<BlogBaseDTO> blogs;
	@Schema(description = "List of comment details")
	private List<CommentBaseDTO> comments;

	// Default Constructor
	public UserDetailsDTO() {
		super();
	}

	// Constructor with all parameters
	public UserDetailsDTO(Long id, String name, int age, String email, String password, String address, String phone,
						  String gender, LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt,
						  List<BlogBaseDTO> blogs, List<CommentBaseDTO> comments) {
		super(id, name, age, email, password, address, phone, gender, dateOfBirth, createdAt, updatedAt);
		this.blogs = blogs;
		this.comments = comments;
	}

	// Constructor without password
	public UserDetailsDTO(Long id, String name, int age, String email, String address, String phone,
						  String gender, LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt,
						  List<BlogBaseDTO> blogs, List<CommentBaseDTO> comments) {
		super(id, name, age, email, address, phone, gender, dateOfBirth, createdAt, updatedAt);
		this.blogs = blogs;
		this.comments = comments;
	}
}
