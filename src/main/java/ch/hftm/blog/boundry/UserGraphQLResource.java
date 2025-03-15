package ch.hftm.blog.boundry;

import java.util.List;


import ch.hftm.blog.control.UserGraphQLService;
import ch.hftm.blog.dto.requerstDTO.*;
import ch.hftm.blog.dto.responseDTO.LoginResponse;
import ch.hftm.blog.entity.User;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
@GraphQLApi
public class UserGraphQLResource {

    @Inject
    UserGraphQLService userService;

    @Query("getAllUsers")
    @RolesAllowed({"User", "Admin"})
    @Description("Get all users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Query("getUserById")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by ID")
    public User getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Query("getUsersByName")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by name")
    public List<User> getUserByName(String name) {
        return userService.getUsersByName(name);
    }

    @Query("getUsersByEmail")
    @RolesAllowed({"User", "Admin"})
    @Description("Get user by email")
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Mutation
    @RolesAllowed({"User", "Admin"})
    @Description("Create a new user")
    public User createUser(UserCreateRequest input) {
        User user = userService.addUser(input);
        return user;
    }

    @Mutation
    @RolesAllowed({"User", "Admin"})
    @Description("Update a user")
    public User updateUser(UserRequest input) {
        User user = userService.updateUser(input);
        return user;
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
            return new LoginResponse(true, token ,user);
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
