package ch.hftm.blog.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BlogTest {

    @Test
    void testBlogCreation() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog = new Blog("Test Blog", "This is a test blog.", user);

        assertNotNull(blog);
        assertEquals("Test Blog", blog.getTitle());
        assertEquals("This is a test blog.", blog.getText());
        assertEquals(user, blog.getUser());
    }

    @Test
    void testBlogEquality() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog1 = new Blog("Test Blog", "This is a test blog.", user);
        Blog blog2 = new Blog("Test Blog", "This is a test blog.", user);

        assertEquals(blog1, blog2);
    }

    @Test
    void testBlogHashCode() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog1 = new Blog("Test Blog", "This is a test blog.", user);
        Blog blog2 = new Blog("Test Blog", "This is a test blog.", user);

        assertEquals(blog1.hashCode(), blog2.hashCode());
    }

    @Test
    void testSetters() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        Blog blog = new Blog();
        blog.setTitle("Updated Blog");
        blog.setText("Updated text.");
        blog.setUser(user);

        assertEquals("Updated Blog", blog.getTitle());
        assertEquals("Updated text.", blog.getText());
        assertEquals(user, blog.getUser());
    }
}
