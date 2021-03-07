package com.example.service;

import com.example.mapper.PostMapper;
import com.example.dto.PostRequest;
import com.example.model.Post;
import com.example.model.User;
import com.example.repo.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<Post> getPostsByUser(User user) {
        return postRepository.findUserPosts(user);
    }

    public void createPost(PostRequest postRequest, User user) {
        postRepository.save(postMapper.mapDto(postRequest, user));
    }

}
