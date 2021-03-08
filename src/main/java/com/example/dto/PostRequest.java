package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String description;
}
