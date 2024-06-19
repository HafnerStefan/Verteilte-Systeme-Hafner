package ch.hftm.blog.repository;

import ch.hftm.blog.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    // Panache stellt die Methoden listAll, findById, persist, count und delete zur Verfügung

    @Transactional
    public User findByName(String name) {
        return find("name", name).firstResult();
    }

    @Transactional
    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
