package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Role;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {

	// Panache stellt die Methoden listAll, findById, persist, count und delete zur Verfügung

	public Role findByName(String name) {
		return find("name", name).firstResult();
	}
}
