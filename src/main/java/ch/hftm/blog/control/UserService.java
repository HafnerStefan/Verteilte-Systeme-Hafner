package ch.hftm.blog.control;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.UserDetailsDTO;
import ch.hftm.blog.dto.UserListDTO;
import ch.hftm.blog.dto.mapper.UserMapper;
import ch.hftm.blog.dto.requerstDTO.LoginRequest;
import ch.hftm.blog.dto.requerstDTO.PasswordChangeRequest;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.Role;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.ObjectNotFoundException;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class UserService {

	@Inject
	UserRepository userRepository;
	@Inject
	BlogService blogService;
	@Inject
	CommentService commentService;

	@Inject
	JsonWebToken jwtToken;



	@Inject
	JWTParser jwtParser;

	@ConfigProperty(name = "jwt.expiration.days", defaultValue = "30")
	int jwtExpirationDays;

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

	public UserBaseDTO getUserBaseDTOById(Long userId) {
		User user = getUserById(userId);
		return UserMapper.toUserBaseDTO(user);
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

	@Transactional
	public UserBaseDTO updateUser(Long id, UserBaseDTO userBaseDTO) {
		User user = getUserById(id);
		user.setName(userBaseDTO.getName());
		user.setAge(userBaseDTO.getAge());

		if (!user.getEmail().equals(userBaseDTO.getEmail())) {
			if (emailExists(userBaseDTO.getEmail())) {
				throw new IllegalArgumentException("Email already exists: " + userBaseDTO.getEmail());
			} else {
				user.setEmail(userBaseDTO.getEmail());
			}
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
	public void changePassword(Long userId, PasswordChangeRequest passwordChangeRequest) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new ObjectNotFoundException("User with " + userId + " not found");
		}

		// Überprüfen, ob der aktuelle Benutzer berechtigt ist, das Passwort zu ändern
		String currentUserEmail = jwtToken.getName();
		Set<String> roles = jwtToken.getGroups();

		if (!user.getEmail().equals(currentUserEmail) && !roles.contains("Admin")) {
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

		Log.info("Password updated for user " + user.getName() + " by " + currentUserEmail);
	}



	public UserBaseDTO authenticateUser(LoginRequest loginRequest) {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException("User not found with email: " + email);
		}
		if (!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("Password is incorrect");
		}
		return UserMapper.toUserBaseDTO(user);
	}



	public String generateJwtToken(String email, Set<Role> roles) {
		long currentTimeInSecs = new Date().getTime() / 1000;
		long expirationTime = currentTimeInSecs + jwtExpirationDays * 24 * 60 * 60;

		// Konvertiere das Set<Role> in ein Set<String> mit den Rollennamen
		Set<String> roleNames = (roles != null) ?
				roles.stream()
						.map(Role::getName)
						.collect(Collectors.toSet()) :
				Collections.emptySet();

		JwtClaimsBuilder claimsBuilder = Jwt.claims()
				.issuer("hftm")  // Definiere den Aussteller des JWT
				.subject(email)
				.upn(email)
				.groups(roleNames)  // Hier werden die Rollennamen als Gruppen gesetzt
				.issuedAt(currentTimeInSecs)
				.expiresAt(expirationTime)
				.claim("email", email);

		return claimsBuilder.sign();
	}



	public void validateJwtToken(String token) throws Exception {
		jwtParser.parse(token); // Token validieren, Exception wird geworfen, wenn das Token ungültig ist
	}


	@Transactional
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId);
		if (user == null) {
			throw new ObjectNotFoundException("User not found with id: " + userId);
		}

		// Überprüfen, ob der aktuelle Benutzer berechtigt ist, diesen Benutzer zu löschen
		String currentUserEmail = jwtToken.getName();
		Set<String> roles = jwtToken.getGroups();

		if (!user.getEmail().equals(currentUserEmail) && !roles.contains("Admin")) {
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
		Log.info("User with ID: " + userId + " deleted by " + currentUserEmail);
	}


	private boolean emailExists(String email) {
		return userRepository.findByEmail(email) != null;
	}

}
