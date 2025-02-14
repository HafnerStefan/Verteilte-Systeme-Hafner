package ch.hftm.blog.dto;

import ch.hftm.blog.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Schema(name = "UsersResponse", description = "DTO for listing users with blog and comment IDs")
public class UserListDTO extends UserBaseDTO {

	@Schema(example = "[12345, 67890, 13579]")
	private List<ObjectId> blogIds;
	@Schema(example = "[98765, 43210, 24680]")
	private List<ObjectId> commentIds;

	// Default Constructor
	public UserListDTO() {
		super();
	}

	// Constructor with all parameters
	public UserListDTO(ObjectId id, String name, int age, String email, String password, String address, String phone,
					   String gender, LocalDate dateOfBirth, Set<Role> roles, LocalDateTime createdAt, LocalDateTime updatedAt,
					   List<ObjectId> blogIds, List<ObjectId> commentIds) {
		super(id, name, age, email, password, address, phone, gender, dateOfBirth,roles, createdAt, updatedAt);
		this.blogIds = blogIds;
		this.commentIds = commentIds;
	}

	// Constructor without password
	public UserListDTO(ObjectId id, String name, int age, String email, String address, String phone,
					   String gender, LocalDate dateOfBirth,Set<Role> roles, LocalDateTime createdAt, LocalDateTime updatedAt,
					   List<ObjectId> blogIds, List<ObjectId> commentIds) {
		super(id, name, age, email, address, phone, gender, dateOfBirth,roles, createdAt, updatedAt);
		this.blogIds = blogIds;
		this.commentIds = commentIds;
	}
}
