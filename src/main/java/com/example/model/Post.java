package com.example.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Instant createdDate;
    @NotBlank(message = "Title is required")
    private String title;
    @Nullable
    @Lob
    private String description;
    private int voteCount;
    private int commentCount;
}
