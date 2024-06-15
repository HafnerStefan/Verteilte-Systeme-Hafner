package ch.hftm.blog.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class BlogServiceTest {
    @Inject
    BlogService blogService;

    @Inject
    UserRepository userRepository;

    @Test
    void listingAndAddingBlogs() {
        // Arrange
        Blog blog = new Blog("Testing Blog", "This is my testing blog");
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
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");

        blogService.addBlog(blog);

        assertNotNull(blog.getId());
    }

    @Test
    @Transactional
    void addUserToBlogTest() {
        // Erstelle ein neues Blog
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");

        // Füge das Blog hinzu
        blogService.addBlog(blog);

        // Erstelle ein neues User-Objekt
        User user = new User();
        user.setName("Alex");

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
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setText("This is a test blog.");

        blogService.addBlog(blog);

        Long blogId = blog.getId();

        blogService.deleteBlog(blogId);

        assertThrows(BlogNotFoundException.class, () -> {
            blogService.getBlogById(blogId);
        });
    }
}
