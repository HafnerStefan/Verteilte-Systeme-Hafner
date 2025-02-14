package ch.hftm.blog.repository;

import java.util.List;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;

import dev.morphia.query.FindOptions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// For Mongo DB
import org.bson.types.ObjectId;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;


@ApplicationScoped
public class UserRepository  {

    @Inject
    Datastore datastore; // Morphia Datastore für MongoDB

    // Panache stellt die Methoden listAll, findById, persist, count und delete zur Verfügung

    public void save(User user) {
        datastore.save(user);
    }

    public void deleteByUser(User user) {
        datastore.find(User.class).filter(Filters.eq("user", user.getId())).delete();
    }

    public List<User> listAll() {
        return listAll(new FindOptions());
    }

    public List<User> listAll(FindOptions options) {
        return datastore.find(User.class).iterator(options).toList();
    }

    public User findById(ObjectId userId) {
        return datastore.find(User.class).filter(Filters.eq("user", userId)).first();
    }

    public List<User> findByName(String name) {
        return datastore.find(User.class).filter(Filters.eq("name", name)).iterator().toList();
    }


    public User findByEmail(String email) {
        return datastore.find(User.class).filter(Filters.eq("email", email)).first();
    }


    public List<User> findByRoleName(String roleName) {
        return datastore.find(User.class)
                .filter(Filters.eq("roles.name", roleName))
                .iterator().toList();
    }



}


