package com.example.controller;

import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import com.example.service.AuthService;
import com.example.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final AuthService authService;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest, authService.getCurrentUser());
        return new ResponseEntity<>("Post has been created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(postService.getPostsByUsername(username), HttpStatus.OK);
    }
}
