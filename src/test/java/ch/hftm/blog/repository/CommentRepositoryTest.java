package ch.hftm.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure that each test method runs in a new instance
@Transactional // Ensure all tests are run within a transaction
public class CommentRepositoryTest {

    @Inject
    CommentRepository commentRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    BlogRepository blogRepository;

    @Test
    void testAddAndRetrieveComment() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogRepository.persist(blog);

        Comment comment = new Comment("Great post!", user, blog);
        commentRepository.persist(comment);

        Comment retrievedComment = commentRepository.findById(comment.getId());
        assertNotNull(retrievedComment);
        assertEquals(comment.getText(), retrievedComment.getText());
    }

    @Test
    void testFindByBlogId() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogRepository.persist(blog);

        Comment comment1 = new Comment("Great post!", user, blog);
        Comment comment2 = new Comment("Nice blog!", user, blog);
        commentRepository.persist(comment1);
        commentRepository.persist(comment2);

        List<Comment> comments = commentRepository.findByBlogId(blog.getId());
        assertEquals(2, comments.size());
        assertTrue(comments.contains(comment1));
        assertTrue(comments.contains(comment2));
    }

    @Test
    void testFindByUserId() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogRepository.persist(blog);

        Comment comment1 = new Comment("Great post!", user, blog);
        Comment comment2 = new Comment("Nice blog!", user, blog);
        commentRepository.persist(comment1);
        commentRepository.persist(comment2);

        List<Comment> comments = commentRepository.findByUserId(user.getId());
        assertEquals(2, comments.size());
        assertTrue(comments.contains(comment1));
        assertTrue(comments.contains(comment2));
    }

    @Test
    void testDeleteComment() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogRepository.persist(blog);

        Comment comment = new Comment("Great post!", user, blog);
        commentRepository.persist(comment);

        Long commentId = comment.getId();
        commentRepository.deleteById(commentId);

        Comment retrievedComment = commentRepository.findById(commentId);
        assertNull(retrievedComment);
    }
}
