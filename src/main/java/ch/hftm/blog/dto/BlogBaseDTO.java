package ch.hftm.blog.dto;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(name = "BlogResponse", description = "Blog entity")
public class BlogBaseDTO {
    @Schema(required = true, example = "32126319")
    private ObjectId id;
    @Schema(required = true, example = "New Blog")
    private String title;
    @Schema(required = true, example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus laoreet eu purus ac congue. Proin aliquam in enim aliquet viverra.")
    private String text;
    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime createdAt;
    @Schema(type = SchemaType.STRING, format = "date-time", example = "2023-01-02T12:00:00")
    private LocalDateTime updatedAt;
    @Schema(example = "1245")
    private ObjectId userId;
    @Schema(description = "Username of the blog author")
    private String username;

    // Default constructor
    public BlogBaseDTO() {
    }

    // Constructor ADD NEW BLOG
    public BlogBaseDTO(String title, String text, ObjectId userId) {
        this.title = title;
        this.text = text;
        this.userId = userId;
    }

    // Constructor without comments
    public BlogBaseDTO(ObjectId id, String title, String text, LocalDateTime createdAt, LocalDateTime updatedAt,
            ObjectId userId,String username) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.username = username;
    }

}
