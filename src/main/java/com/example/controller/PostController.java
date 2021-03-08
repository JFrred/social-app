package com.example.controller;

import com.example.dto.PostRequest;
import com.example.exception.SpringSocialAppException;
import com.example.model.PostResponse;
import com.example.model.User;
import com.example.repo.UserRepository;
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
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest, authService.getCurrentUser());
        return new ResponseEntity<>("Post has been created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new SpringSocialAppException("Could not find user with username " + username));

        List<PostResponse> userPosts = postService.getPostsByUser(user);
        return new ResponseEntity<>(userPosts, HttpStatus.OK);
    }
}
