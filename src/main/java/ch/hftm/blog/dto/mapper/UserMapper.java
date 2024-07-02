package ch.hftm.blog.dto.mapper;

import ch.hftm.blog.dto.BlogBaseDTO;
import ch.hftm.blog.dto.CommentDTO;
import ch.hftm.blog.dto.UserBaseDTO;
import ch.hftm.blog.dto.UserDetailsDTO;
import ch.hftm.blog.dto.UserListDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;

import java.util.ArrayList;
import java.util.List;

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
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                );
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
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        blogIds,
                        commentIds
                );
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
                                                blog.getUser().getId()

                                        // Comments are set separately
                                );
                                blogBaseDTOS.add(blogBaseDTO);
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

                return new UserDetailsDTO(
                        user.getId(),
                        user.getName(),
                        user.getAge(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getPhone(),
                        user.getGender(),
                        user.getDateOfBirth(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        blogBaseDTOS,
                        commentDTOs
                );
        }

        public static User toUser(UserBaseDTO userBaseDTO) {
                User user = new User();
                user.setId(userBaseDTO.getId());
                user.setName(userBaseDTO.getName());
                user.setAge(userBaseDTO.getAge());
                user.setEmail(userBaseDTO.getEmail());
                user.setPassword(userBaseDTO.getPassword());
                user.setAddress(userBaseDTO.getAddress());
                user.setPhone(userBaseDTO.getPhone());
                user.setGender(userBaseDTO.getGender());
                user.setDateOfBirth(userBaseDTO.getDateOfBirth());
                user.setCreatedAt(userBaseDTO.getCreatedAt());
                user.setUpdatedAt(userBaseDTO.getUpdatedAt());
                return user;
        }
}
