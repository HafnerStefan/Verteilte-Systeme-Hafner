package ch.hftm.blog.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.BlogRepository;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure that each test method runs in a new instance
@Transactional // Ensure all tests are run within a transaction
public class CommentServiceTest {

    @Inject
    CommentService commentService;

    @Inject
    UserService userService;

    @Inject
    BlogService blogService;

    @Inject
    UserRepository userRepository;

    @Inject
    BlogRepository blogRepository;

    @Test
    void listingAndAddingComments() {
        // Arrange
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogService.addBlog(blog);

        Comment comment = new Comment("Great post!", user, blog);
        int commentsBefore;
        List<Comment> comments;

        // Act
        commentsBefore = commentService.getComments().size();

        commentService.addComment(comment);
        comments = commentService.getComments();

        // Assert
        assertEquals(commentsBefore + 1, comments.size());
        assertEquals(comment, comments.get(comments.size() - 1));
    }

    @Test
    void addCommentTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogService.addBlog(blog);

        Comment comment = new Comment("Great post!", user, blog);
        commentService.addComment(comment);

        assertNotNull(comment.getId());
        assertNotNull(comment.getCreatedAt());
    }

    @Test
    void getCommentByIdTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogService.addBlog(blog);

        Comment comment = new Comment("Great post!", user, blog);
        commentService.addComment(comment);

        Comment retrievedComment = commentService.getCommentById(comment.getId());

        assertEquals(comment.getId(), retrievedComment.getId());
        assertEquals(comment.getText(), retrievedComment.getText());
    }

    @Test
    void getCommentByIdNotFoundTest() {
        assertThrows(ObjectNotFoundException.class, () -> commentService.getCommentById(999L));
    }

    @Test
    void updateCommentTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogService.addBlog(blog);

        Comment comment = new Comment("Great post!", user, blog);
        commentService.addComment(comment);

        comment.setText("Updated comment.");
        commentService.updateComment(comment.getId(), comment);

        Comment updatedComment = commentService.getCommentById(comment.getId());

        assertEquals("Updated comment.", updatedComment.getText());
    }

    @Test
    void deleteCommentTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogService.addBlog(blog);

        Comment comment = new Comment("Great post!", user, blog);
        commentService.addComment(comment);

        Long commentId = comment.getId();

        commentService.deleteComment(commentId);

        assertThrows(ObjectNotFoundException.class, () -> {
            commentService.getCommentById(commentId);
        });
    }

    @Test
    void getCommentsByBlogIdTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogService.addBlog(blog);

        Comment comment1 = new Comment("Great post!", user, blog);
        Comment comment2 = new Comment("Nice blog!", user, blog);
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        List<Comment> comments = commentService.getCommentsByBlogId(blog.getId());

        assertEquals(2, comments.size());
        assertTrue(comments.contains(comment1));
        assertTrue(comments.contains(comment2));
    }

    @Test
    void getCommentsByUserIdTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Blog blog = new Blog("Test Blog", "This is a test blog.", user);
        blogService.addBlog(blog);

        Comment comment1 = new Comment("Great post!", user, blog);
        Comment comment2 = new Comment("Nice blog!", user, blog);
        commentService.addComment(comment1);
        commentService.addComment(comment2);

        List<Comment> comments = commentService.getCommentsByUserId(user.getId());

        assertEquals(2, comments.size());
        assertTrue(comments.contains(comment1));
        assertTrue(comments.contains(comment2));
    }
}
