package ch.hftm.blog.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import ch.hftm.blog.entity.User;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    @Transactional
    void testUserPersistence() {
        // Erstelle ein neues User-Objekt
        User user = new User();
        user.setName("Test User");

        // Persistiere das User-Objekt
        userRepository.persist(user);

        // Überprüfe, ob das User-Objekt eine ID zugewiesen bekommen hat
        assertNotNull(user.getId());
    }
}