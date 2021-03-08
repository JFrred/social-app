package com.example.service;

import com.example.mapper.PostMapper;
import com.example.dto.PostRequest;
import com.example.model.PostResponse;
import com.example.model.User;
import com.example.repo.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public List<PostResponse> getPostsByUser(User user) {
        return postRepository.findByUser(user).stream().map(postMapper::mapToResponse).collect(Collectors.toList());
    }


    @Transactional
    public void createPost(PostRequest postRequest, User user) {
        postRepository.save(postMapper.mapDto(postRequest, user));
    }

}
