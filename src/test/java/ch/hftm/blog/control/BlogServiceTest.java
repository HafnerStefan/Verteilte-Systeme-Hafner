package ch.hftm.blog.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure that each test method runs in a new instance
@Transactional // Ensure all tests are run within a transaction
public class BlogServiceTest {
    @Inject
    BlogService blogService;

    @Inject
    UserRepository userRepository;

    @Test
    void listingAndAddingBlogs() {
        // Arrange
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Testing Blog", "This is my testing blog", user);
        int blogsBefore;
        List<Blog> blogs;

        // Act
        blogsBefore = blogService.getBlogs().size();

        blogService.addBlog(blog);
        blogs = blogService.getBlogs();

        // Assert
        assertEquals(blogsBefore + 1, blogs.size());
        assertEquals(blog, blogs.get(blogs.size() - 1));
    }

    @Test
    void addBlogTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");
        blog.setUser(user);

        blogService.addBlog(blog);

        assertNotNull(blog.getId());
        assertNotNull(blog.getCreatedAt());
        assertNotNull(blog.getUpdatedAt());
    }

    @Test
    void getBlogByIdTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");
        blog.setUser(user);
        blogService.addBlog(blog);

        Blog retrievedBlog = blogService.getBlogById(blog.getId());

        assertEquals(blog.getId(), retrievedBlog.getId());
        assertEquals(blog.getTitle(), retrievedBlog.getTitle());
    }

    @Test
    void getBlogByIdNotFoundTest() {
        assertThrows(ObjectNotFoundException.class, () -> blogService.getBlogById(999L));
    }

    @Test
    void updateBlogTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");
        blog.setUser(user);
        blogService.addBlog(blog);

        blog.setTitle("Updated Blog");
        blog.setText("This blog has been updated.");
        blogService.updateBlog(blog.getId(), blog);

        Blog updatedBlog = blogService.getBlogById(blog.getId());

        assertEquals("Updated Blog", updatedBlog.getTitle());
        assertEquals("This blog has been updated.", updatedBlog.getText());
        assertNotNull(updatedBlog.getUpdatedAt());
    }

    @Test
    void addUserToBlogTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        // Erstelle ein neues Blog
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");
        blogService.addBlog(blog);

        // Persistiere das User-Objekt
        userRepository.persist(user);

        // Füge den Benutzer zum Blog hinzu
        blogService.addUserToBlog(blog.getId(), user);

        // Überprüfe, ob das User-Objekt nicht null ist
        assertNotNull(user.getId());

        // Überprüfe, ob das User-Objekt erfolgreich dem Blog zugeordnet wurde
        assertEquals(user.getId(), blog.getUser().getId());
    }

    @Test
    void deleteBlogTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");
        blog.setUser(user);
        blogService.addBlog(blog);

        Long blogId = blog.getId();

        blogService.deleteBlog(blogId);

        assertThrows(ObjectNotFoundException.class, () -> {
            blogService.getBlogById(blogId);
        });
    }

    @Test
    void getBlogsByUserIdTest() {
        // Create a user
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        // Create blogs and associate them with the user
        Blog blog1 = new Blog("Blog 1", "Content for blog 1", user);
        Blog blog2 = new Blog("Blog 2", "Content for blog 2", user);
        blogService.addBlog(blog1);
        blogService.addBlog(blog2);

        List<Blog> blogs = blogService.getBlogsByUserId(user.getId());

        assertEquals(2, blogs.size());
        assertTrue(blogs.contains(blog1));
        assertTrue(blogs.contains(blog2));
    }
}
