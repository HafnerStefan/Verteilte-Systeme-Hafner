package ch.hftm.blog.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import ch.hftm.blog.dto.*;
import ch.hftm.blog.dto.requerstDTO.UserCreateRequest;
import ch.hftm.blog.dto.requerstDTO.UserRequest;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;

public class UserMapper {

    public static UserBaseDTO toUserBaseDTO(User user) {
        return new UserBaseDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getRolesList(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static UserBaseDTO toUserBaseDTO(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }

        UserBaseDTO userBaseDTO = new UserBaseDTO();
        userBaseDTO.setName(userRequest.getName());
        userBaseDTO.setAge(userRequest.getAge());
        userBaseDTO.setEmail(userRequest.getEmail());
        userBaseDTO.setAddress(userRequest.getAddress());
        userBaseDTO.setPhone(userRequest.getPhone());
        userBaseDTO.setGender(userRequest.getGender());
        userBaseDTO.setDateOfBirth(userRequest.getDateOfBirth());

        return userBaseDTO;
    }

    public static UserListDTO toUserListDTO(User user) {
        List<Long> blogIds = new ArrayList<>();
        if (user.getBlogs() != null) {
            for (Blog blog : user.getBlogs()) {
                blogIds.add(blog.getId());
            }
        }

        List<Long> commentIds = new ArrayList<>();
        if (user.getComments() != null) {
            for (Comment comment : user.getComments()) {
                commentIds.add(comment.getId());
            }
        }

        return new UserListDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getRolesList(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                blogIds,
                commentIds);
    }

    public static UserDetailsDTO toUserDetailsDTO(User user) {
        List<BlogBaseDTO> blogBaseDTOS = new ArrayList<>();
        if (user.getBlogs() != null) {
            for (Blog blog : user.getBlogs()) {
                BlogBaseDTO blogBaseDTO = new BlogBaseDTO(
                        blog.getId(),
                        blog.getTitle(),
                        blog.getText(),
                        blog.getCreatedAt(),
                        blog.getUpdatedAt(),
                        blog.getUser().getId(),
                        blog.getUser().getName()

                        // Comments are set separately
                );
                blogBaseDTOS.add(blogBaseDTO);
            }
        }

        List<CommentBaseDTO> commentBaseDTOS = new ArrayList<>();
        if (user.getComments() != null) {
            for (Comment comment : user.getComments()) {
                CommentBaseDTO commentBaseDTO = new CommentBaseDTO(
                        comment.getId(),
                        comment.getText(),
                        comment.getCreatedAt(),
                        comment.getBlog().getId(),
                        comment.getUser().getId());
                commentBaseDTOS.add(commentBaseDTO);
            }
        }

        return new UserDetailsDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getRolesList(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                blogBaseDTOS,
                commentBaseDTOS);
    }

    public static User toUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setId(userCreateRequest.getId());
        user.setName(userCreateRequest.getName());
        user.setAge(userCreateRequest.getAge());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userCreateRequest.getPassword());
        user.setAddress(userCreateRequest.getAddress());
        user.setPhone(userCreateRequest.getPhone());
        user.setGender(userCreateRequest.getGender());
        user.setDateOfBirth(userCreateRequest.getDateOfBirth());
        user.setCreatedAt(userCreateRequest.getCreatedAt());
        user.setUpdatedAt(userCreateRequest.getUpdatedAt());
        return user;
    }
}
