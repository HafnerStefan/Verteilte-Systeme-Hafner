package ch.hftm.blog.entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue

    private long id;

    String name;
    String age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Blog> blog;

    public User() {

    }

    /**
    * @return the id
    */
    public long getId() {
        return id;
    }

    /**
     * @param name
     * @param age
     */
    public User(String name, String age) {
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
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
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
