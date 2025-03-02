package ch.hftm.blog.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.time.LocalDateTime;


@Schema(name = "CommentWithBlogTitleResponse", description = "DTO for comments with blog title")
public class CommentWithBlogTitleDTO extends CommentBaseDTO {
	@Schema(required = true, example = "My Blog Title")
	private String blogTitle;

	// Default constructor
	public CommentWithBlogTitleDTO() {
		super();
	}

	// Constructor with parameters
	public CommentWithBlogTitleDTO(Long id, String text, LocalDateTime createdAt, Long blogId, Long userId, String blogTitle) {
		super(id, text, createdAt, blogId, userId);
		this.blogTitle = blogTitle;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
}
