package ch.hftm.blog.control;

import java.util.List;

import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;

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

    @GET
    public long count() {
        return userRepository.count();
    }
}