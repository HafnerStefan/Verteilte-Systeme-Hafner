package ch.hftm.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity("roles")
//@Table(name = "roles")
public class Role {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ObjectId id;

	@NotBlank(message = "Role name must not be blank")
	//@Column(unique = true, nullable = false)
	private String name;

	//@ManyToMany(mappedBy = "roles")
	@Reference
	@JsonBackReference
	private Set<User> users = new HashSet<>();

	public Role() {
		// Standardkonstruktor
	}

	public Role(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Role role = (Role) o;
		return Objects.equals(id, role.id) && Objects.equals(name, role.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
