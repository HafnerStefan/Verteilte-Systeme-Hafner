package ch.hftm.blog.dto;

import ch.hftm.blog.entity.Role;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


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

	// Constructor without password
	public UserDetailsDTO(Long id,
						  String name,
						  int age,
						  String email,
						  String address,
						  String phone,
						  String gender,
						  LocalDate dateOfBirth,
						  List<String> roles,
						  LocalDateTime createdAt,
						  LocalDateTime updatedAt,
						  List<BlogBaseDTO> blogs,
						  List<CommentBaseDTO> comments
	) {
		super(id, name, age, email, address, phone, gender, dateOfBirth,roles, createdAt, updatedAt);
		this.blogs = blogs;
		this.comments = comments;
	}

	public List<CommentBaseDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentBaseDTO> comments) {
		this.comments = comments;
	}
}
