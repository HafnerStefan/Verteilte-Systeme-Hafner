package ch.hftm.blog.boundry;

import java.util.List;

public class BlogRequest {
    private String title;
    private String text;
    private Long userId;
    private List<Long> commentIds;

    // Standardkonstruktor
    public BlogRequest() {
    }

    // Konstruktor mit Parametern
    public BlogRequest(String title, String text, Long userId, List<Long> commentIds) {
        this.title = title;
        this.text = text;
        this.userId = userId;
        this.commentIds = commentIds;

    }

    // Getter und Setter
    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Long> getCommentIds() {
        return commentIds;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }

}
