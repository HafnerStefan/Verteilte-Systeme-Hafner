package ch.hftm.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure that each test method runs in a new instance
@Transactional // Ensure all tests are run within a transaction
public class BlogRepositoryTest {

    @Inject
    BlogRepository blogRepository;

    @Inject
    UserRepository userRepository;

    @Test
    void testAddAndRetrieveBlog() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogRepository.persist(blog);

        Blog retrievedBlog = blogRepository.findById(blog.getId());
        assertNotNull(retrievedBlog);
        assertEquals(blog.getTitle(), retrievedBlog.getTitle());
    }

    @Test
    void testFindByTitle() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogRepository.persist(blog);

        Blog retrievedBlog = blogRepository.findByTitle("Test Blog");
        assertNotNull(retrievedBlog);
        assertEquals(blog.getTitle(), retrievedBlog.getTitle());
    }

    @Test
    void testFindByUserId() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog1 = new Blog("Test Blog 1", "This is a test blog 1.", user);
        Blog blog2 = new Blog("Test Blog 2", "This is a test blog 2.", user);
        blogRepository.persist(blog1);
        blogRepository.persist(blog2);

        List<Blog> blogs = blogRepository.findByUserId(user.getId());
        assertEquals(2, blogs.size());
        assertTrue(blogs.contains(blog1));
        assertTrue(blogs.contains(blog2));
    }

    @Test
    void testDeleteBlog() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogRepository.persist(blog);

        Long blogId = blog.getId();
        blogRepository.deleteById(blogId);

        Blog retrievedBlog = blogRepository.findById(blogId);
        assertNull(retrievedBlog);
    }
}
