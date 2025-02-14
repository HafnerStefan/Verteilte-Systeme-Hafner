package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Role;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class RoleRepository {

	@Inject
	Datastore datastore; // Morphia Datastore für MongoDB

	public void save(Role role) {
		datastore.save(role);
	}

	public List<Role> findAll() {
		return datastore.find(Role.class).iterator().toList();
	}

	public Role findById(String id) {
		return datastore.find(Role.class).filter(Filters.eq("_id", id)).first();
	}

	public Role findByName(String name) {
		return datastore.find(Role.class).filter(Filters.eq("name", name)).first();
	}

	public void deleteById(String id) {
		datastore.find(Role.class).filter(Filters.eq("_id", id)).delete();
	}
}
