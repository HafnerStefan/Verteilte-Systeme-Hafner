package ch.hftm.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class UserDTO {
    // Getter und Setter
    private Long id;
    private String name;
    private int age;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<BlogDTO> blogs;
    private List<CommentDTO> comments;

    // Standardkonstruktor
    public UserDTO() {
    }

    // Konstruktor mit minimum an Parameter
    public UserDTO(Long id, String name, int age, String email, String address, String phone,
            String gender,
            LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    // Constructor without Created and Update Date
    public UserDTO(Long id, String name, int age, String email, String address, String phone,
            String gender,
            LocalDate dateOfBirth, List<BlogDTO> blogs,
            List<CommentDTO> comments) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.blogs = blogs;
        this.comments = comments;
    }

    // Constructor with all parameters
    public UserDTO(Long id, String name, int age, String email, String password, String address, String phone,
            String gender,
            LocalDate dateOfBirth, LocalDateTime createdAt, LocalDateTime updatedAt, List<BlogDTO> blogs,
            List<CommentDTO> comments) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.blogs = blogs;
        this.comments = comments;
    }

}
