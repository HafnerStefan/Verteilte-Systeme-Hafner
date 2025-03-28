package ch.hftm.blog.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.BlogBaseDTO;
import ch.hftm.blog.dto.BlogDetailsDTO;
import ch.hftm.blog.dto.BlogListDTO;
import ch.hftm.blog.dto.CommentDetailDTO;
import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.requerstDTO.BlogRequest;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;

public class BlogMapper {

    public static BlogBaseDTO toBlogBaseDTO(Blog blog) {
        return new BlogBaseDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getText(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getUser().getId(),
                blog.getUser().getName());

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
                blog.getUser().getName(),
                commentIds);
    }

    public static BlogDetailsDTO toBlogDetailsDTO(Blog blog) {
        List<Comment> comments = blog.getComments();
        List<CommentDetailDTO> commentDetailDTOS = new ArrayList<>();
        if (comments != null) {
            commentDetailDTOS = comments.stream()
                    .map(CommentMapper::toCommentDetailDTO) // Verwende CommentDetailDTO
                    .collect(Collectors.toList());
        }

        UserBaseDTO userDTO = UserMapper.toUserBaseDTO(blog.getUser());
        String username = blog.getUser().getName();

        return new BlogDetailsDTO(
                blog.getId(),
                blog.getTitle(),
                blog.getText(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getUser().getId(),
                commentDetailDTOS,
                userDTO,
                username);
    }
}
