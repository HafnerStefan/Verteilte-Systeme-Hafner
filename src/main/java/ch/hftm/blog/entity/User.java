package ch.hftm.blog.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Version;
import ch.hftm.blog.boundry.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Setter
@Getter
@Entity
@Table(name = "user")
//@Schema(name = "User", description = "User entity")
public class User {

    // Getter und Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Schema(required = true, example = "32126319")
    private Long id;

    //@Schema(required = true, example = "Sandra Dubeli")
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    //@Schema(required = true, example = "32")
    @NotNull(message = "Age must not be null")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 150, message = "Age must be less than or equal to 150")
    private int age;

    //@Schema(required = true, example = "sandra.dubeli@example.com")
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    //@Schema(required = true, example = "password123")
    @NotBlank(message = "Password must not be blank", groups = ValidationGroups.Create.class)
    @Size(min = 8, message = "Password must be at least 8 characters long", groups = ValidationGroups.Create.class)
    private String password;

    //@Schema(example = "123 Main St, Anytown, AT 12345")
    private String address;

    //@Schema(example = "+41 78 965 26 15")
    @Pattern(regexp = "\\+\\d{2} \\d{2} \\d{3} \\d{2} \\d{2}", message = "Phone number must follow the pattern +XX XX XXX XX XX")
    private String phone;

    //@Schema(example = "female")
    @Pattern(regexp = "male|female|other", message = "Gender must be either 'male', 'female', or 'other'")
    private String gender;

    //@Schema(example = "1988-12-31")
    @NotNull(message = "Date of birth must not be null")
    @Past(message = "Date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    //@Schema(example = "2023-01-01T12:00:00")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //@Schema(example = "2023-01-02T12:00:00")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    //@Schema(allOf = Blog.class)
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

}
