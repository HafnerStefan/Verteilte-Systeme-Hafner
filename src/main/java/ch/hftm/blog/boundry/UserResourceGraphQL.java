package ch.hftm.blog.boundry;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hftm.blog.control.UserGraphQLService;
import ch.hftm.blog.dto.UserGraphQL_DTO;
import ch.hftm.blog.dto.requerstDTO.*;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.UserRepository;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.vertx.codegen.doc.Token;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.UserDetailsDTO;
import ch.hftm.blog.dto.UserListDTO;
import ch.hftm.blog.dto.mapper.UserMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@GraphQLApi
public class UserResourceGraphQL {

    @Inject
    UserRepository userRepository;

    @Inject
    JsonWebToken jwtToken;

    @Inject
    JWTParser jwtParser;

    @Inject
    UserGraphQLService userGraphQLService;

    @Inject
    UserService userService;

    @Query("getAllUsers")
    @RolesAllowed({"User", "Admin"})
    @Description("Get all users")
    public List<UserGraphQL_DTO> getAllUsers() {
        return userGraphQLService.getAllUsers();
    }

    @Query("getUserById")
    @PermitAll
    @Description("Get user by ID")
    public UserGraphQL_DTO getUserById(Long id) {
        return userGraphQLService.getUserById(id);
    }

    @Query("getUsersByName")
    @PermitAll
    @Description("Get user by name")
    public List<UserGraphQL_DTO> getUserByName(String name) {
        return userGraphQLService.getUsersByName(name);
    }

    @Query("getUsersByEmail")
    @PermitAll
    @Description("Get user by email")
    public UserGraphQL_DTO getUserByEmail(String email) {
        return userGraphQLService.getUserByEmail(email);
    }

    @Mutation
    @PermitAll
    @Description("Create a new user")
    public UserGraphQL_DTO createUser(UserCreateRequest input) {
        User user = userService.addUser(UserMapper.toUserBaseDTO(input));
        return UserMapper.toUserGraphQL_DTO(user);
    }

    @Mutation
    @PermitAll
    @Description("Update a user")
    public UserGraphQL_DTO updateUser(UserRequest input) {
        User user = userService.updateUser(UserMapper.toUserBaseDTO(input));
        return UserMapper.toUserGraphQL_DTO(user);
    }

    @Mutation
    @PermitAll
    @Description("Delete a user")
    public boolean deleteUser(Long id) {
        userService.deleteUser(id);
        return true;
    }

    @Mutation
    @PermitAll
    @Description("Login")
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.authenticateUser(loginRequest);
        if (user != null) {
            String token = userService.generateJwtToken(user.getId());
            return new LoginResponse(true, token ,UserMapper.toUserGraphQL_DTO(user));
        } else {
            return new LoginResponse(false, null, null);
        }
    }
}
