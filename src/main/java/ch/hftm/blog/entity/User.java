package ch.hftm.blog.entity;

import java.util.List;
import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Schema(name = "User", description = "User returne")
public class User {

    @Id
    @GeneratedValue
    @Schema(required = true, example = "32126319")
    private Long id;
    @Schema(required = true, example = "Sandra Dubeli")
    String name;
    @Schema(required = true, example = "32")
    int age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Schema(allOf = Blog.class)
    @JsonManagedReference
    private List<Blog> blog;

    public User() {

    }

    /**
    * @return the id
    */
    public Long getId() {
        return id;
    }

    /**
     * @param name
     * @param age
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
    * @return the blog
    */
    public List<Blog> getBlog() {
        return blog;
    }

    /**
     * @param blog the blog to set
     */
    public void setBlog(List<Blog> blog) {
        this.blog = blog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

}
