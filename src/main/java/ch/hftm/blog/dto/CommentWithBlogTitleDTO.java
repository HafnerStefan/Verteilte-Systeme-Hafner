package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(name = "CommentWithBlogTitleResponse", description = "DTO for comments with blog title")
public class CommentWithBlogTitleDTO extends CommentBaseDTO {
	@Schema(required = true, example = "My Blog Title")
	private String blogTitle;

	// Default constructor
	public CommentWithBlogTitleDTO() {
		super();
	}

	// Constructor with parameters
	public CommentWithBlogTitleDTO(ObjectId id, String text, LocalDateTime createdAt, ObjectId blogId, ObjectId userId, String blogTitle) {
		super(id, text, createdAt, blogId, userId);
		this.blogTitle = blogTitle;
	}
}
