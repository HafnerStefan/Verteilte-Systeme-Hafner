package ch.hftm.blog.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CommentTest {

    @Test
    void testCommentCreation() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        Comment comment = new Comment("Great post!", user, blog);

        assertNotNull(comment);
        assertEquals("Great post!", comment.getText());
        assertEquals(user, comment.getUser());
        assertEquals(blog, comment.getBlog());
    }

    @Test
    void testCommentEquality() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        Comment comment1 = new Comment("Great post!", user, blog);
        Comment comment2 = new Comment("Great post!", user, blog);

        assertEquals(comment1, comment2);
    }

    @Test
    void testCommentHashCode() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        Comment comment1 = new Comment("Great post!", user, blog);
        Comment comment2 = new Comment("Great post!", user, blog);

        assertEquals(comment1.hashCode(), comment2.hashCode());
    }

    @Test
    void testSetters() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        Comment comment = new Comment();
        comment.setText("Updated comment.");
        comment.setUser(user);
        comment.setBlog(blog);

        assertEquals("Updated comment.", comment.getText());
        assertEquals(user, comment.getUser());
        assertEquals(blog, comment.getBlog());
    }
}
