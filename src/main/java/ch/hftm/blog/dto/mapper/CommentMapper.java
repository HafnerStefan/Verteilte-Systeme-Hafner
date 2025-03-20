package ch.hftm.blog.dto.mapper;

import ch.hftm.blog.dto.CommentBaseDTO;
import ch.hftm.blog.dto.CommentDetailDTO;
import ch.hftm.blog.dto.requerstDTO.CommentRequest;
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

    public static Comment toComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setCreatedAt(commentRequest.getCreatedAt());
        comment.setBlog(commentRequest.getBlog());
        comment.setUser(commentRequest.getUser());

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
}
