package ch.hftm.blog.boundry;

import java.util.List;


import ch.hftm.blog.control.UserGraphQLService;
import ch.hftm.blog.dto.UserGraphQL_DTO;
import ch.hftm.blog.dto.requerstDTO.*;
import ch.hftm.blog.dto.responseDTO.LoginResponse;
import ch.hftm.blog.entity.User;


import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;


import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.mapper.UserMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
@GraphQLApi
public class UserGraphQLResource {

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
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by ID")
    public UserGraphQL_DTO getUserById(Long id) {
        return userGraphQLService.getUserById(id);
    }

    @Query("getUsersByName")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by name")
    public List<UserGraphQL_DTO> getUserByName(String name) {
        return userGraphQLService.getUsersByName(name);
    }

    @Query("getUsersByEmail")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by email")
    public UserGraphQL_DTO getUserByEmail(String email) {
        return userGraphQLService.getUserByEmail(email);
    }

    @Mutation
    @RolesAllowed({"User", "Admin"})
    @Description("Create a new user")
    public UserGraphQL_DTO createUser(UserCreateRequest input) {
        User user = userService.addUser(UserMapper.toUserBaseDTO(input));
        return UserMapper.toUserGraphQL_DTO(user);
    }

    @Mutation
    @RolesAllowed({"User", "Admin"})
    @Description("Update a user")
    public UserGraphQL_DTO updateUser(UserRequest input) {
        User user = userService.updateUser(UserMapper.toUserBaseDTO(input));
        return UserMapper.toUserGraphQL_DTO(user);
    }

    @Mutation
    @RolesAllowed({"Admin"})
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

    @Mutation
    @RolesAllowed({"Admin"})
    @Description("change password")
    public boolean changePassword(Long userId,PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(userId,passwordChangeRequest);
        return true;
    }
}
