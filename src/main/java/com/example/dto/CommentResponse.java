package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Integer commentId;
    private Instant createdDate;
    private String username;
    private String text;
}
