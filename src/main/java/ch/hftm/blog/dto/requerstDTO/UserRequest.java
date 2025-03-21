package ch.hftm.blog.dto.requerstDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "UserRequest", description = "Request payload for creating or updating a user")
@Name("UserRequest")
public class UserRequest {

    // Getter
    @Schema(example = "32126319")
    private Long id;

    @Schema(required = true, example = "Sandra Dubeli")
    @NonNull
    private String name;

    @Schema(required = true, example = "32")
    private int age;

    @Schema(required = true, example = "sandra.dubeli@example.com")
    @NonNull
    private String email;

    @Schema(example = "123 Main St, Anytown, AT 12345")
    private String address;

    @Schema(example = "+41 78 965 26 15")
    private String phone;

    @Schema(example = "female")
    private String gender;

    @Schema(type = SchemaType.STRING, format = "date", example = "1988-12-31")
    private LocalDate dateOfBirth;

    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime createdAt;

    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime updatedAt;


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
}
