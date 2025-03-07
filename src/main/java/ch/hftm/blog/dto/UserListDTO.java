package ch.hftm.blog.dto;

import ch.hftm.blog.entity.Role;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Schema(name = "UsersResponse", description = "DTO for listing users with blog and comment IDs")
public class UserListDTO extends UserBaseDTO {

	@Schema(example = "[12345, 67890, 13579]")
	private List<Long> blogIds;
	@Schema(example = "[98765, 43210, 24680]")
	private List<Long> commentIds;

	// Default Constructor
	public UserListDTO() {
		super();
	}

	// Constructor with all parameters
	public UserListDTO(Long id, String name, int age, String email, String password, String address, String phone,
					   String gender, LocalDate dateOfBirth, List<String> roles, LocalDateTime createdAt, LocalDateTime updatedAt,
					   List<Long> blogIds, List<Long> commentIds) {
		super(id, name, age, email, password, address, phone, gender, dateOfBirth, roles, createdAt, updatedAt);
		this.blogIds = blogIds;
		this.commentIds = commentIds;
	}

	// Constructor without password
	public UserListDTO(Long id, String name, int age, String email, String address, String phone,
					   String gender, LocalDate dateOfBirth,List<String> roles, LocalDateTime createdAt, LocalDateTime updatedAt,
					   List<Long> blogIds, List<Long> commentIds) {
		super(id, name, age, email, address, phone, gender, dateOfBirth, roles, createdAt, updatedAt);
		this.blogIds = blogIds;
		this.commentIds = commentIds;
	}

	// Getter and Setter

	public List<Long> getBlogIds() {
		return blogIds;
	}

	public void setBlogIds(List<Long> blogIds) {
		this.blogIds = blogIds;
	}

	public List<Long> getCommentIds() {
		return commentIds;
	}

	public void setCommentIds(List<Long> commentIds) {
		this.commentIds = commentIds;
	}
}
