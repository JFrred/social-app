package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String username;
    private Instant createdDate;
    private String title;
    private String description;
    private Integer voteCount;
    private Integer commentCount;
}
