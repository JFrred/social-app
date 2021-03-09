package com.example.service;

import com.example.exception.SpringSocialAppException;
import com.example.mapper.PostMapper;
import com.example.dto.PostRequest;
import com.example.dto.PostResponse;
import com.example.model.User;
import com.example.repo.PostRepository;
import com.example.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Transactional
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new SpringSocialAppException("Could not find user with username " + username));
        return postRepository.findByUser(user)
                .stream().map(postMapper::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void createPost(PostRequest postRequest, User user) {
        postRepository.save(postMapper.mapDto(postRequest, user));
    }
}
