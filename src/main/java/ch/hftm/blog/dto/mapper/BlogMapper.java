package ch.hftm.blog.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.BlogDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;

public class BlogMapper {

    // Convert Blog entity to BlogDTO
    public static BlogDTO toBlogDTO(Blog blog) {
        List<Long> commentIds = blog.getComments() != null ? blog.getComments().stream()
                .map(Comment::getId)
                .collect(Collectors.toList()) : null;
        return new BlogDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getText(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getUser().getId(),
                commentIds);

    }

    // Convert BlogDTO to Blog entity
    public static Blog toBlog(BlogDTO blogDTO) {
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle());
        blog.setText(blogDTO.getText());
        blog.setCreatedAt(blogDTO.getCreatedAt());
        blog.setUpdatedAt(blogDTO.getUpdatedAt());
        // User and Comments are set separately
        return blog;
    }
}
