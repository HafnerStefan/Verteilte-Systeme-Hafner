package ch.hftm.blog.dto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;


@Schema(name = "BlogsResponse", description = "DTO for listing blogs with comment IDs")
public class BlogListDTO extends BlogBaseDTO {

	@Schema(description = "List of comment IDs", example = "[46516,4584,62,14,2]")
	private List<Long> commentsIds;

	// Default constructor
	public BlogListDTO() {
		super();
	}

	// Constructor with all fields
	public BlogListDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId,String username,
					   List<Long> commentsIds) {
		super(id, title, text, createdAt, updatedAt, userId,username);
		this.commentsIds = commentsIds;
	}

	// Constructor with basic fields
	public BlogListDTO(Long id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId,String username) {
		super(id, title, text, createdAt, updatedAt, userId,username);
	}

	// Getter and Setter


	public List<Long> getCommentsIds() {
		return commentsIds;
	}

	public void setCommentsIds(List<Long> commentsIds) {
		this.commentsIds = commentsIds;
	}
}
