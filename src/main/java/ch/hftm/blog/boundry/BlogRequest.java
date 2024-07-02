package ch.hftm.blog.boundry;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BlogRequest {

    private String title;
    private String text;
    private Long userId;
    private List<Long> commentIds;


    public BlogRequest() {
    }


    public BlogRequest(String title, String text, Long userId) {
        this.title = title;
        this.text = text;
        this.userId = userId;

    }


    public BlogRequest(String title, String text, Long userId, List<Long> commentIds) {
        this.title = title;
        this.text = text;
        this.userId = userId;
        this.commentIds = commentIds;

    }

}
