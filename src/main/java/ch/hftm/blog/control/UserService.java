package ch.hftm.blog.control;

import ch.hftm.blog.dto.mapper.UserMapper;
import ch.hftm.blog.dto.requerstDTO.*;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.Role;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    JsonWebToken jwtToken;

    @Inject
    JWTParser jwtParser;

    @ConfigProperty(name = "jwt.expiration.days", defaultValue = "30")
    int jwtExpirationDays;

    public PaginationResponse<User> getUsers(PaginationParams paginationRequest) {
        int page = paginationRequest.page;
        int size = paginationRequest.size;
        String sortOrder = paginationRequest.sortOrder;

        List<User> users;

        if ("desc".equalsIgnoreCase(sortOrder)) {
            users = userRepository.findAll(Sort.by("updatedAt").descending())
                    .page(Page.of(page, size))
                    .list();
        } else {
            users = userRepository.findAll(Sort.by("updatedAt").ascending())
                    .page(Page.of(page, size))
                    .list();
        }
        long totalElements = userRepository.count();
        Log.info("Fetched " + users.size() + " blogs from page " + page);

        return new PaginationResponse<>(users, totalElements, page, size);
    }

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            return  user;
        } else {
            throw new ObjectNotFoundException("User not found with ID: " + userId);
        }
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return  user;
        } else {
            throw new ObjectNotFoundException("User not found with email: " + email);
        }
    }

    public List<User> getUsersByName(String name) {
        List<User> users = userRepository.findByName(name);
        if (users != null && !users.isEmpty()) {
            return users;
        } else {
            throw new ObjectNotFoundException("User not found with name: " + name);
        }
    }

    @Transactional
    public User addUser(UserCreateRequest userCreateRequest) {
        if (emailExists(userCreateRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userCreateRequest.getEmail());
        }

        User user = UserMapper.toUser(userCreateRequest);
        Log.info("Adding User " + user.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public User updateUser(UserRequest userRequest) {
        User user = getUserById(userRequest.getId());
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        if (!user.getEmail().equals(userRequest.getEmail())) {
            if (emailExists(userRequest.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + userRequest.getEmail());
            } else {
                user.setEmail(userRequest.getEmail());
            }
        }

        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setGender(userRequest.getGender());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setUpdatedAt(LocalDateTime.now());
        Log.info("Updating User " + user.getName());
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("User not found with id: " + userId);
        }

        long currentUserId = Long.parseLong(jwtToken.getSubject());
        Set<String> roles = jwtToken.getGroups();

        if (user.getId() != currentUserId && !roles.contains("Admin")) {
            throw new UnauthorizedException("You are not allowed to delete this user");
        }

        for (Blog userBlog : user.getBlogs()) {
            if (userBlog.getComments() != null) {
                for (Comment comment : userBlog.getComments()) {
                    comment.getUser().getComments().remove(comment);
                }
            }
        }
        for (Comment userComment : user.getComments()) {
            if (userComment != null) {
                userComment.getBlog().getComments().remove(userComment);
            }
        }
        userRepository.delete(user);
        Log.info("User with ID: " + userId + " deleted" );
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Transactional
    public void changePassword(Long userId, PasswordChangeRequest passwordChangeRequest) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("User with " + userId + " not found");
        }


        long currentUserId = Long.parseLong(jwtToken.getSubject());
        Set<String> roles = jwtToken.getGroups();

        if (user.getId() != currentUserId && !roles.contains("Admin")) {
            throw new UnauthorizedException("You are not allowed to change the password for this user");
        }

        // Überprüfung des alten Passworts
        if (!user.getPassword().equals(passwordChangeRequest.getOldPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Validierung des neuen Passworts
        if (passwordChangeRequest.getNewPassword() == null || passwordChangeRequest.getNewPassword().isEmpty()) {
            throw new IllegalArgumentException("New password must not be empty");
        }

        // Setzen des neuen Passworts
        user.setPassword(passwordChangeRequest.getNewPassword());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.persist(user);

        Log.info("Password updated for user " + user.getName() + " by " + currentUserId);
    }

    public User authenticateUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ObjectNotFoundException("User not found with email: " + email);
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        return user;
    }

    public String generateJwtToken(Long userId) {
        long currentTimeInSecs = new Date().getTime() / 1000;
        long expirationTime = currentTimeInSecs + jwtExpirationDays * 24 * 60 * 60;

        User user = userRepository.findById(userId);
        Set<Role> roles = user.getRoles();

        Set<String> roleNames = (roles != null) ?
                roles.stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()) :
                Collections.emptySet();

        JwtClaimsBuilder claimsBuilder = Jwt.claims()
                .issuer("hftm")
                .subject(userId.toString())
                .groups(roleNames)
                .issuedAt(currentTimeInSecs)
                .expiresAt(expirationTime);

        return claimsBuilder.sign();
    }

    @Transactional
    public User validateJwtToken(String token) throws Exception {
        JsonWebToken jwt = jwtParser.parse(token); // Token validieren und analysieren

        // Benutzer-ID aus dem Token extrahieren
        String userIdStr = jwt.getSubject(); // Annahme: Benutzer-ID ist im 'subject' des Tokens
        Long userId = Long.parseLong(userIdStr);

        // Benutzer anhand der Benutzer-ID suchen
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("User not found with id: " + userId);
        }

        // Benutzer in UserBaseDTO umwandeln und zurückgeben
        return user;
    }


}
