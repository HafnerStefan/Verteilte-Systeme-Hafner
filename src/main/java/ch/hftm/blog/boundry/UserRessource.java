package ch.hftm.blog.boundry;

import java.util.List;

import ch.hftm.blog.control.UserService;
import ch.hftm.blog.entity.User;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("user") // Unter welchem Web-Pfad die Ressource erreichbar ist. Diese Annotation darfst du zusätzlich auch direkt über der Methode anbringen
@ApplicationScoped

public class UserRessource {

    @Inject
    UserService userService;

    @GET // Diese Methode ist über eine http-GET-Anfrage erreichbar.
    public List<User> getUsers() {
        var users = this.userService.getUsers();
        Log.info("Returning " + users.size() + " users");
        return users;
    }

    @POST // Diese Methode ist über eine http-POST-Anfrage erreichbar.
    public void addUser(User user) {
        this.userService.addUser(user);
        Log.info("Adding User " + user.getName());

    }

}
