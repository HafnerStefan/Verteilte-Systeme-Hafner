package ch.hftm.blog.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void testUserCreation() {
        User user = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);

        assertNotNull(user);
        assertEquals("Sandra Dubeli", user.getName());
        assertEquals(32, user.getAge());
        assertEquals("sandra.dubeli@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("female", user.getGender());
    }

    @Test
    void testUserEquality() {
        User user1 = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        User user2 = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);

        assertEquals(user1, user2);
    }

    @Test
    void testUserHashCode() {
        User user1 = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);
        User user2 = new User("Sandra Dubeli", 32, "sandra.dubeli@example.com", "password123", "female", null);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testSetters() {
        User user = new User();
        user.setName("Alex");
        user.setAge(25);
        user.setEmail("alex@example.com");
        user.setPassword("pass123");
        user.setGender("male");

        assertEquals("Alex", user.getName());
        assertEquals(25, user.getAge());
        assertEquals("alex@example.com", user.getEmail());
        assertEquals("pass123", user.getPassword());
        assertEquals("male", user.getGender());
    }
}
