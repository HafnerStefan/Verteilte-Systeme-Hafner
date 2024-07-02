package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.UserDTO;
import ch.hftm.blog.dto.mapper.UserMapper;
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

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.listAll();
        Log.info("Returning " + users.size() + " users");

        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = UserMapper.toUserDTO(user);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Transactional
    public UserDTO addUser(UserDTO userDTO) {
        if (emailExists(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }
        User user = UserMapper.toUser(userDTO);
        Log.info("Adding User " + user.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.persist(user);
        return UserMapper.toUserDTO(user);
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

    public UserDTO getUserDTOById(Long userId) {
        User user = getUserById(userId);
        return UserMapper.toUserDTO(user);
    }

    public List<UserDTO> getUsersByName(String name) {
        List<User> users = userRepository.findByName(name);
        if (users != null && !users.isEmpty()) {
            users.forEach(user -> {
                user.getBlogs().size(); // Force initialization
                user.getComments().size(); // Force initialization
            });
            return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
        } else {
            throw new ObjectNotFoundException("User not found with name: " + name);
        }
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.getBlogs().size(); // Force initialization
            user.getComments().size(); // Force initialization
            return UserMapper.toUserDTO(user);
        } else {
            throw new ObjectNotFoundException("User not found with email: " + email);
        }
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = getUserById(id);
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());

        if (!user.getEmail().equals(userDTO.getEmail()) && emailExists(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(userDTO.getPassword());
        }
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating User " + user.getName());
        userRepository.persist(user);
        return UserMapper.toUserDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        Log.info("Deleting User " + user.getName());
        userRepository.delete(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
