package ch.hftm.blog.boundry;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequest {

    private String text;
    private Long userId;
    private Long blogId;


    public CommentRequest() {
    }


    public CommentRequest(String text, Long userId, Long blogId) {
        this.text = text;
        this.userId = userId;
        this.blogId = blogId;
    }

}
