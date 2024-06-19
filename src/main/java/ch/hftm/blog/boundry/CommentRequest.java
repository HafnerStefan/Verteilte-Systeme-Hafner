package ch.hftm.blog.boundry;

public class CommentRequest {
    private String text;
    private Long userId;
    private Long blogId;

    // Standardkonstruktor
    public CommentRequest() {
    }

    // Konstruktor mit Parametern
    public CommentRequest(String text, Long userId, Long blogId) {
        this.text = text;
        this.userId = userId;
        this.blogId = blogId;
    }

    // Getter und Setter
    public String getText() {
        return text;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

}
