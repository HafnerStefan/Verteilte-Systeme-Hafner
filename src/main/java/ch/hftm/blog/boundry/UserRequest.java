package ch.hftm.blog.boundry;

import java.time.LocalDate;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "UserRequest", description = "Request payload for creating or updating a user")
public class UserRequest {

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

    // Getter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
