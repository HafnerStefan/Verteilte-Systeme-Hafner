package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDTO {

    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private Long blogId;
    private Long userId;


    public CommentDTO() {
    }

    public CommentDTO( String text, Long blogId, Long userId) {
        this.text = text;
        this.blogId = blogId;
        this.userId = userId;
    }


    public CommentDTO(Long id, String text, LocalDateTime createdAt, Long blogId, Long userId) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.blogId = blogId;
        this.userId = userId;
    }

}
