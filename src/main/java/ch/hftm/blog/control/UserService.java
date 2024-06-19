package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.List;

import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> getUsers() {
        var users = userRepository.listAll();
        Log.info("Returning " + users.size() + " users");
        return users;
    }

    @Transactional
    public void addUser(User user) {
        Log.info("Adding User " + user.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.persist(user);
    }

    public long count() {
        return userRepository.count();
    }

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            return user;
        } else {
            throw new ObjectNotFoundException("User not found with ID: " + userId);
        }
    }

    public User getUserByName(String name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            return user;
        } else {
            throw new ObjectNotFoundException("User not found with name: " + name);
        }
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        } else {
            throw new ObjectNotFoundException("User not found with email: " + email);
        }
    }

    @Transactional
    public void updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setAge(userDetails.getAge());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setAddress(userDetails.getAddress());
        user.setPhone(userDetails.getPhone());
        user.setGender(userDetails.getGender());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating User " + user.getName());
        userRepository.persist(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        Log.info("Deleting User " + user.getName());
        userRepository.delete(user);
    }
}
