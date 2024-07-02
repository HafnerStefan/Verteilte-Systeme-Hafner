package ch.hftm.blog.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import ch.hftm.blog.dto.BlogDTO;
import ch.hftm.blog.dto.CommentDTO;
import ch.hftm.blog.dto.UserDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;

public class UserMapper {

        // Convert User entity to UserDTO
        public static UserDTO toUserDTO(User user) {
                List<BlogDTO> blogDTOs = new ArrayList<>();
                if (user.getBlogs() != null) {
                        for (Blog blog : user.getBlogs()) {
                                BlogDTO blogDTO = new BlogDTO(
                                                blog.getId(),
                                                blog.getTitle(),
                                                blog.getText(),
                                                blog.getCreatedAt(),
                                                blog.getUpdatedAt(),
                                                blog.getUser().getId(),
                                                null // Comments are set separately
                                );
                                blogDTOs.add(blogDTO);
                        }
                }

                List<CommentDTO> commentDTOs = new ArrayList<>();
                if (user.getComments() != null) {
                        for (Comment comment : user.getComments()) {
                                CommentDTO commentDTO = new CommentDTO(
                                                comment.getId(),
                                                comment.getText(),
                                                comment.getCreatedAt(),
                                                comment.getBlog().getId(),
                                                comment.getUser().getId());
                                commentDTOs.add(commentDTO);
                        }
                }

                return new UserDTO(
                                user.getId(),
                                user.getName(),
                                user.getAge(),
                                user.getEmail(),
                                user.getPassword(),
                                user.getAddress(),
                                user.getPhone(),
                                user.getGender(),
                                user.getDateOfBirth(),
                                user.getCreatedAt(),
                                user.getUpdatedAt(),
                                blogDTOs,
                                commentDTOs);
        }

        // Convert UserDTO to User entity
        public static User toUser(UserDTO userDTO) {
                User user = new User();
                user.setId(userDTO.getId());
                user.setName(userDTO.getName());
                user.setAge(userDTO.getAge());
                user.setEmail(userDTO.getEmail());
                user.setPassword(userDTO.getPassword());
                user.setAddress(userDTO.getAddress());
                user.setPhone(userDTO.getPhone());
                user.setGender(userDTO.getGender());
                user.setDateOfBirth(userDTO.getDateOfBirth());
                user.setCreatedAt(userDTO.getCreatedAt());
                user.setUpdatedAt(userDTO.getUpdatedAt());
                // Blogs and Comments are usually set separately
                return user;
        }
}
