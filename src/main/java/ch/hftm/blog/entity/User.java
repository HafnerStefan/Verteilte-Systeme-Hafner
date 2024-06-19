package ch.hftm.blog.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Schema(name = "User", description = "User entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(required = true, example = "32126319")
    private Long id;

    @Schema(required = true, example = "Sandra Dubeli")
    private String name;

    @Schema(required = true, example = "32")
    private int age;

    @Schema(required = true, example = "sandra.dubeli@example.com")
    private String email;

    @Schema(required = true, example = "password123")
    private String password;

    @Schema(example = "123 Main St, Anytown, AT 12345")
    private String address;

    @Schema(example = "+41 78 965 26 15")
    private String phone;

    @Schema(example = "female")
    private String gender;

    @Schema(example = "1988-12-31")
    private LocalDate dateOfBirth;

    @Schema(example = "2023-01-01T12:00:00")
    private LocalDateTime createdAt;

    @Schema(example = "2023-01-02T12:00:00")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Schema(allOf = Blog.class)
    @JsonManagedReference
    private List<Blog> blogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;

    public User() {
        // Standardkonstruktor
    }

    public User(String name, int age, String email, String password, String gender,
            LocalDate dateOfBirth) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String name, int age, String email, String password, String address, String phone, String gender,
            LocalDate dateOfBirth) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(name, user.name)
                && Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && Objects.equals(address, user.address) && Objects.equals(phone, user.phone)
                && Objects.equals(gender, user.gender) && Objects.equals(dateOfBirth, user.dateOfBirth)
                && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, password, address, phone, gender, dateOfBirth, createdAt, updatedAt);
    }
}
