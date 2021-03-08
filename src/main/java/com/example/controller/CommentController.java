package com.example.controller;

import com.example.dto.CommentRequest;
import com.example.dto.CommentResponse;
import com.example.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}/comments")
    public List<CommentResponse> getPostComments(@PathVariable Long postId) {
        return commentService.getPostComments(postId);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<String> createComment(@PathVariable Long postId,
                                                @RequestBody CommentRequest commentRequest) {
        commentService.createComment(postId, commentRequest);
        return new ResponseEntity<>("The comment has been created", HttpStatus.CREATED);
    }
}
