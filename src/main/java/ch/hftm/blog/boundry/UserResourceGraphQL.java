package ch.hftm.blog.boundry;

import java.util.List;


import ch.hftm.blog.control.UserGraphQLService;
import ch.hftm.blog.dto.UserGraphQL_DTO;
import ch.hftm.blog.dto.requerstDTO.*;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.exception.GraphQLExceptionHandler;
import ch.hftm.blog.repository.UserRepository;
import io.smallrye.jwt.auth.principal.JWTParser;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.jwt.JsonWebToken;


import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.mapper.UserMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
@GraphQLApi
public class UserResourceGraphQL {

    @Inject
    UserGraphQLService userGraphQLService;

    @Inject
    UserService userService;


    @Query("getAllUsers")
    @RolesAllowed({"User", "Admin"})
    @Description("Get all users")
    public List<UserGraphQL_DTO> getAllUsers() {
        try {
            return userGraphQLService.getAllUsers();
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }

    @Query("getUserById")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by ID")
    public UserGraphQL_DTO getUserById(Long id) {
        try {
            return userGraphQLService.getUserById(id);
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }

    @Query("getUsersByName")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by name")
    public List<UserGraphQL_DTO> getUserByName(String name) {
        try {
            return userGraphQLService.getUsersByName(name);
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }

    @Query("getUsersByEmail")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by email")
    public UserGraphQL_DTO getUserByEmail(String email) {
        try {
            return userGraphQLService.getUserByEmail(email);
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }

    @Mutation
    @PermitAll
    @Description("Create a new user")
    public UserGraphQL_DTO createUser(UserCreateRequest input) {
        try {
            User user = userService.addUser(UserMapper.toUserBaseDTO(input));
            return UserMapper.toUserGraphQL_DTO(user);
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }

    @Mutation
    @RolesAllowed({"User", "Admin"})
    @Description("Update a user")
    public UserGraphQL_DTO updateUser(UserRequest input) {

        try {
            User user = userService.updateUser(UserMapper.toUserBaseDTO(input));
            return UserMapper.toUserGraphQL_DTO(user);
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }

    @Mutation
    @RolesAllowed({"User", "Admin"})
    @Description("Delete a user")
    public boolean deleteUser(Long id) {
        try {
            userService.deleteUser(id);
            return true;
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }

    @Mutation
    @PermitAll
    @Description("Login")
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest);
            if (user != null) {
                String token = userService.generateJwtToken(user.getId());
                return new LoginResponse(true, token, UserMapper.toUserGraphQL_DTO(user));
            } else {
                return new LoginResponse(false, null, null);
            }
        } catch (Exception e) {
            throw GraphQLExceptionHandler.handleGraphQLException(e);
        }
    }
}
