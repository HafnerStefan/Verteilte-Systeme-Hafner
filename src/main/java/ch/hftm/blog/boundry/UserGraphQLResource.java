package ch.hftm.blog.boundry;

import java.util.List;

import ch.hftm.blog.control.UserService;
import ch.hftm.blog.dto.requerstDTO.*;
import ch.hftm.blog.dto.responseDTO.LoginResponse;
import ch.hftm.blog.dto.responseDTO.PaginationResponse;
import ch.hftm.blog.entity.User;

import io.quarkus.logging.Log;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;


@ApplicationScoped
@GraphQLApi
public class UserGraphQLResource {

    @Inject
    CurrentVertxRequest request;

    @Inject
    UserService userService;

    BroadcastProcessor<User> processor = BroadcastProcessor.create();

    @Query("getUsers")
    @RolesAllowed({"User", "Admin"})
    @Description("Get all users")
    public PaginationResponse<User> getUsers(PaginationParams paginationParams) {
        PaginationResponse<User> Users = userService.getUsers(paginationParams);
        Log.info("Returning " + Users.getContent().size() + " users");
        return Users;
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
        processor.onNext(user);
        return user;
    }

    @Subscription
    @PermitAll
    public Multi<User> userCreated(){
        return processor;
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

    @POST
    @Path("/validate-token")
    @PermitAll
    @Query("validateToken")
    public User validateToken() {
        String authorizationHeader = request.getCurrent().request().getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Unauthorized: Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            User user = userService.validateJwtToken(token);
            return user; // Token ist gültig
        } catch (Exception e) {
            throw new UnauthorizedException("Unauthorized: Invalid or expired token");
        }
    }

}
