package ch.hftm.blog.dto;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import jakarta.validation.constraints.*;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Schema(name = "UserResponse", description = "Base DTO for user")
@Type("UserDTO")
public class UserGraphQL_DTO {

    @NonNull
    @Description("Unique user ID")
    private Long id;

    @NonNull
    @Description("Full name of the user")
    private String name;

    @NonNull
    private int age;

    @NonNull
    private String email;

    private String address;

    private String phone;

    private String gender;

    private LocalDate dateOfBirth;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> roles;

    private List<Blog> blogs;

    private List<Comment> comments;


    // Default Constructor
    public UserGraphQL_DTO() {
    }

    public UserGraphQL_DTO(Long id,
                           String name,
                           int age,
                           String email,
                           String address,
                           String phone,
                           String gender,
                           LocalDate dateOfBirth,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           List<String> roles,
                           List<Blog> blogs,
                           List<Comment> comments
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
        this.blogs = blogs;
        this.comments = comments;
    }

    public UserGraphQL_DTO(Long id,
                           String name,
                           int age,
                           String email,
                           String address,
                           String phone,
                           String gender,
                           LocalDate dateOfBirth,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    // Getter and Setter


    public @NonNull Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public @NonNull String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public int getAge() {
        return age;
    }

    public void setAge(@NonNull int age) {
        this.age = age;
    }

    public @NonNull String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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


