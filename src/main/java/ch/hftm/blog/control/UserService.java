package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.UserListDTO;
import ch.hftm.blog.dto.UserDetailsDTO;
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

    public List<UserListDTO> getUsers() {
        List<User> users = userRepository.listAll();
        Log.info("Returning " + users.size() + " users");

        List<UserListDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserListDTO userDTO = UserMapper.toUserListDTO(user);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Transactional
    public UserBaseDTO addUser(UserBaseDTO userDTO) {
        if (emailExists(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }
        User user = UserMapper.toUser(userDTO);
        Log.info("Adding User " + user.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.persist(user);
        return UserMapper.toUserBaseDTO(user);
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

    public UserDetailsDTO getUserDTOById(Long userId) {
        User user = getUserById(userId);
        return UserMapper.toUserDetailsDTO(user);
    }

    public List<UserDetailsDTO> getUsersByName(String name) {
        List<User> users = userRepository.findByName(name);
        if (users != null && !users.isEmpty()) {
            users.forEach(user -> {
                user.getBlogs().size(); // Force initialization
                user.getComments().size(); // Force initialization
            });
            return users.stream().map(UserMapper::toUserDetailsDTO).collect(Collectors.toList());
        } else {
            throw new ObjectNotFoundException("User not found with name: " + name);
        }
    }

    public UserDetailsDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.getBlogs().size(); // Force initialization
            user.getComments().size(); // Force initialization
            return UserMapper.toUserDetailsDTO(user);
        } else {
            throw new ObjectNotFoundException("User not found with email: " + email);
        }
    }

    @Transactional
    public UserBaseDTO updateUser(Long id, UserBaseDTO userBaseDTO) {
        User user = getUserById(id);
        user.setName(userBaseDTO.getName());
        user.setAge(userBaseDTO.getAge());

        if (!user.getEmail().equals(userBaseDTO.getEmail()) && emailExists(userBaseDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userBaseDTO.getEmail());
        }
        if (userBaseDTO.getPassword() != null && !userBaseDTO.getPassword().isEmpty()) {
            user.setPassword(userBaseDTO.getPassword());
        }
        user.setAddress(userBaseDTO.getAddress());
        user.setPhone(userBaseDTO.getPhone());
        user.setGender(userBaseDTO.getGender());
        user.setDateOfBirth(userBaseDTO.getDateOfBirth());
        user.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating User " + user.getName());
        userRepository.persist(user);
        return UserMapper.toUserBaseDTO(user);
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
