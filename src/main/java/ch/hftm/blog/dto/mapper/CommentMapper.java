package ch.hftm.blog.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.CommentBaseDTO;
import ch.hftm.blog.dto.CommentDetailDTO;
import ch.hftm.blog.dto.CommentWithBlogContextDTO;
import ch.hftm.blog.dto.CommentWithBlogTitleDTO;
import ch.hftm.blog.entity.Comment;

public class CommentMapper {

    public static CommentBaseDTO toCommentBaseDTO(Comment comment) {
        return new CommentBaseDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getBlog().getId(),
                comment.getUser().getId());
    }

    public static Comment toComment(CommentBaseDTO commentBaseDTO) {
        Comment comment = new Comment();
        comment.setText(commentBaseDTO.getText());
        comment.setCreatedAt(commentBaseDTO.getCreatedAt());

        // Blog and user are usually set separately
        return comment;
    }

    public static CommentDetailDTO toCommentDetailDTO(Comment comment) {
        String username = comment.getUser().getName();

        return new CommentDetailDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getBlog().getId(),
                comment.getUser().getId(),
                username);
    }

    public static CommentWithBlogContextDTO toCommentWithBlogContextDTO(Comment comment, List<Comment> previousComments,
            List<Comment> nextComments) {
        List<CommentBaseDTO> previousCommentDTOs = previousComments.stream()
                .map(CommentMapper::toCommentBaseDTO)
                .collect(Collectors.toList());

        List<CommentBaseDTO> nextCommentDTOs = nextComments.stream()
                .map(CommentMapper::toCommentBaseDTO)
                .collect(Collectors.toList());

        return new CommentWithBlogContextDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getBlog().getId(),
                comment.getUser().getId(),
                comment.getBlog().getTitle(),
                previousCommentDTOs,
                nextCommentDTOs);
    }

    public static CommentWithBlogTitleDTO toCommentWithBlogTitleDTO(Comment comment) {
        return new CommentWithBlogTitleDTO(
                comment.getId(),
                comment.getText(),
                comment.getCreatedAt(),
                comment.getBlog().getId(),
                comment.getUser().getId(),
                comment.getBlog().getTitle());
    }

}
