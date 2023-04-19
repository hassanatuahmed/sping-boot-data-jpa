package com.example.blogapi.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Setter
@Getter
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 0 , message="Post title should have at least 2 characters ")
    private String title;

    @NotEmpty
    @Size(min = 10 ,message="Post Description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
