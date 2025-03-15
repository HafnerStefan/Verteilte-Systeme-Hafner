package ch.hftm.blog.control;

import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.UserDetailsDTO;
import ch.hftm.blog.dto.UserGraphQL_DTO;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@ApplicationScoped
public class UserGraphQLService {

    @Inject
    UserRepository userRepository;

    public List<UserGraphQL_DTO> getAllUsers() {
        List<User> users = userRepository.listAll(); // Annahme: findAll() gibt alle User zurück
        Log.info("Returning " + users.size() + " users");

        return users.stream()
                .map(UserMapper::toUserGraphQL_DTO)
                .collect(Collectors.toList());
    }

    public UserGraphQL_DTO getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            return  UserMapper.toUserGraphQL_DTO(user);
        } else {
            throw new ObjectNotFoundException("User not found with ID: " + userId);
        }
    }

    public UserGraphQL_DTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return  UserMapper.toUserGraphQL_DTO(user);
        } else {
            throw new ObjectNotFoundException("User not found with email: " + email);
        }
    }

    public List<UserGraphQL_DTO> getUsersByName(String name) {
        List<User> users = userRepository.findByName(name);
        if (users != null && !users.isEmpty()) {
            return users.stream().map(UserMapper::toUserGraphQL_DTO).collect(Collectors.toList());
        } else {
            throw new ObjectNotFoundException("User not found with name: " + name);
        }
    }


}
