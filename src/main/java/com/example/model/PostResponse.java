package com.example.model;

import lombok.*;

import java.time.Instant;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Integer postId;
    private String username;
    private String title;
    private String description;
    private Instant createdDate;
    private int voteCount;
    private int commentCount;
}
