package ch.hftm.blog.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure that each test method runs in a new instance
@Transactional // Ensure all tests are run within a transaction
public class UserServiceTest {

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Test
    void listingAndAddingUsers() {
        // Arrange
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        int usersBefore;
        List<User> users;

        // Act
        usersBefore = userService.getUsers().size();

        userService.addUser(user);
        users = userService.getUsers();

        // Assert
        assertEquals(usersBefore + 1, users.size());
        assertEquals(user, users.get(users.size() - 1));
    }

    @Test
    void addUserTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);

        userService.addUser(user);

        assertNotNull(user.getId());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void getUserByIdTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        User retrievedUser = userService.getUserById(user.getId());

        assertEquals(user.getId(), retrievedUser.getId());
        assertEquals(user.getName(), retrievedUser.getName());
    }

    @Test
    void getUserByIdNotFoundTest() {
        assertThrows(ObjectNotFoundException.class, () -> userService.getUserById(999L));
    }

    @Test
    void updateUserTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        user.setName("Updated User");
        userService.updateUser(user.getId(), user);

        User updatedUser = userService.getUserById(user.getId());

        assertEquals("Updated User", updatedUser.getName());
        assertNotNull(updatedUser.getUpdatedAt());
    }

    @Test
    void deleteUserTest() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userService.addUser(user);

        Long userId = user.getId();

        userService.deleteUser(userId);

        assertThrows(ObjectNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
    }

}
