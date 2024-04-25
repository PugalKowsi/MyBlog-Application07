package com.myblog7.Payload;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post Title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 4, message = "Post Content should have at least 4 characters")
    private String content;

    @NotEmpty
    @Size(min = 5, message = "Post Description should have at least 5 characters")
    private String description;
    }

