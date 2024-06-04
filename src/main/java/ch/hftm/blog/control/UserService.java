package ch.hftm.blog.control;

import java.util.List;

import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
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
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

}