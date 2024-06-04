package ch.hftm.blog.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Blog blog;
        int blogsBefore;
        List<Blog> blogs;

        // Act
        blogsBefore = blogService.getBlogs().size();

        long id = blogService.addBlog("Testing Blog", "This is my testing blog");
        blog = blogService.getBlogById(id);
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

        blogService.addBlog("Test Blog", "This is a test blog.");

        assertNotNull(blog.getId());
    }

    @Test
    @Transactional
    void addUserToBlogTest() {
        // Erstelle ein neues Blog

        // Füge das Blog hinzu
        long id = blogService.addBlog("Test Blog", "This is a test blog.");
        Blog blog = blogService.getBlogById(id);

        // Erstelle ein neues User-Objekt
        User user = new User();
        user.setName("Alex");

        // Persistiere das User-Objekt
        userRepository.persist(user);

        // Füge den Benutzer zum Blog hinzu
        blogService.addUserToBlog(id, user);

        // Überprüfe, ob das User-Objekt nicht null ist
        assertNotNull(user.getId());

        // Überprüfe, ob das User-Objekt erfolgreich dem Blog zugeordnet wurde
        assertEquals(user.getId(), blog.getUser().getId());
    }
    /* 
    
    @Test
    void deleteBlogTest() {
    
        long id = blogService.addBlog("Test Blog", "This is a test blog.");
        System.out.println("ID: " + id);
        Blog blog = blogService.getBlogById(id);
    
        Long blogId = blog.getId();
        System.out.println("Blog ID: " + blogId);
        Blog delBlog = blogService.deleteBlog(blogId);
        System.out.println(delBlog.toString());
        System.out.println("TEST*******************************");
        List<Blog> blogs = blogService.getBlogs();
        for (Blog b : blogs) {
            System.out.println(b.toString());
        }
        delBlog = blogService.getBlogById(blogId);
    
        assertThrows(IllegalArgumentException.class, () -> {
            blogService.getBlogById(blogId);
        });
    }
    */

}
