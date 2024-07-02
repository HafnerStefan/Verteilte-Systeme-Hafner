package ch.hftm.blog.dto.mapper;

import ch.hftm.blog.dto.*;

import ch.hftm.blog.dto.mapper.CommentMapper;
import ch.hftm.blog.dto.mapper.UserMapper;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlogMapper {

    public static BlogBaseDTO toBlogBaseDTO(Blog blog) {
        return new BlogBaseDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getText(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getUser().getId()
        );
    }

    public static BlogListDTO toBlogListDTO(Blog blog) {
        List<Long> commentIds = new ArrayList<>();
        if (blog.getComments() != null) {
            for (Comment comment : blog.getComments()) {
                commentIds.add(comment.getId());
            }
        }

        return new BlogListDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getText(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getUser().getId(),
                commentIds
        );
    }

    public static BlogDetailsDTO toBlogDetailsDTO(Blog blog) {
        List<CommentDTO> commentDTOs = new ArrayList<>();
        if (blog.getComments() != null) {
            commentDTOs = blog.getComments().stream()
                    .map(CommentMapper::toCommentDTO)
                    .collect(Collectors.toList());
        }

        UserBaseDTO userDTO = UserMapper.toUserBaseDTO(blog.getUser());

        return new BlogDetailsDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getText(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                commentDTOs,
                userDTO
        );
    }

    public static Blog toBlog(BlogBaseDTO blogBaseDTO) {
        Blog blog = new Blog();
        blog.setTitle(blogBaseDTO.getTitle());
        blog.setText(blogBaseDTO.getText());
        blog.setCreatedAt(blogBaseDTO.getCreatedAt());
        blog.setUpdatedAt(blogBaseDTO.getUpdatedAt());
        // User and comments are usually set separately
        return blog;
    }
}
