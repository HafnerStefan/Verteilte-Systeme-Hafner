package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Schema(name = "BlogsResponse", description = "DTO for listing blogs with comment IDs")
public class BlogListDTO extends BlogBaseDTO {

	@Schema(description = "List of comment IDs", example = "[46516,4584,62,14,2]")
	private List<ObjectId> commentsIds;

	// Default constructor
	public BlogListDTO() {
		super();
	}

	// Constructor with all fields
	public BlogListDTO(ObjectId id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, ObjectId userId, String username,
					   List<ObjectId> commentsIds) {
		super(id, title, text, createdAt, updatedAt, userId,username);
		this.commentsIds = commentsIds;
	}

	// Constructor with basic fields
	public BlogListDTO(ObjectId id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, ObjectId userId,String username) {
		super(id, title, text, createdAt, updatedAt, userId,username);
	}
}
