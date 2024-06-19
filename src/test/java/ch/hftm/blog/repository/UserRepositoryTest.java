package ch.hftm.blog.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import ch.hftm.blog.entity.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Ensure that each test method runs in a new instance
@Transactional // Ensure all tests are run within a transaction
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    void testAddAndRetrieveUser() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        User retrievedUser = userRepository.findById(user.getId());
        assertNotNull(retrievedUser);
        assertEquals(user.getName(), retrievedUser.getName());
    }

    @Test
    void testFindByName() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        User retrievedUser = userRepository.findByName("Test User");
        assertNotNull(retrievedUser);
        assertEquals(user.getName(), retrievedUser.getName());
    }

    @Test
    void testFindByEmail() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        User retrievedUser = userRepository.findByEmail("test@example.com");
        assertNotNull(retrievedUser);
        assertEquals(user.getEmail(), retrievedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        User user = new User("Test User", 30, "test@example.com", "password", "123 Main St", "555-5555", "male", null);
        userRepository.persist(user);

        Long userId = user.getId();
        userRepository.deleteById(userId);

        User retrievedUser = userRepository.findById(userId);
        assertNull(retrievedUser);
    }
}
