package ch.hftm.blog.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ch.hftm.blog.boundry.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.graphql.Ignore;


@Entity
@Table(name = "user")
public class User {

    // Getter und Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Age must not be null")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private int age;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class)
    @Size(min = 8, message = "Password must be at least 8 characters long", groups = ValidationGroups.Create.class)
    @Ignore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    @Ignore
    private Set<Role> roles = new HashSet<>();

    private String address;

    @Pattern(regexp = "\\+\\d{2} \\d{2} \\d{3} \\d{2} \\d{2}", message = "Phone number must follow the pattern +XX XX XXX XX XX")
    private String phone;

    @Pattern(regexp = "male|female|other", message = "Gender must be either 'male', 'female', or 'other'")
    private String gender;

    @NotNull(message = "Date of birth must not be null")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Blog> blogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    // Getter and Setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name must not be blank") @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name must not be blank") @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters") String name) {
        this.name = name;
    }

    @NotNull(message = "Age must not be null")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    public int getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "Age must not be null") @Min(value = 0, message = "Age must be at least 0") @Max(value = 150, message = "Age must be less than or equal to 150") int age) {
        this.age = age;
    }

    public @NotBlank(message = "Email must not be blank") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email must not be blank") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class) @Size(min = 8, message = "Password must be at least 8 characters long", groups = ValidationGroups.Create.class) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class) @Size(min = 8, message = "Password must be at least 8 characters long", groups = ValidationGroups.Create.class) String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<String> getRolesList() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public @Pattern(regexp = "\\+\\d{2} \\d{2} \\d{3} \\d{2} \\d{2}", message = "Phone number must follow the pattern +XX XX XXX XX XX") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "\\+\\d{2} \\d{2} \\d{3} \\d{2} \\d{2}", message = "Phone number must follow the pattern +XX XX XXX XX XX") String phone) {
        this.phone = phone;
    }

    public @Pattern(regexp = "male|female|other", message = "Gender must be either 'male', 'female', or 'other'") String getGender() {
        return gender;
    }

    public void setGender(@Pattern(regexp = "male|female|other", message = "Gender must be either 'male', 'female', or 'other'") String gender) {
        this.gender = gender;
    }

    public @NotNull(message = "Date of birth must not be null") @Past(message = "Date of birth must be in the past") LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull(message = "Date of birth must not be null") @Past(message = "Date of birth must be in the past") LocalDate dateOfBirth) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
