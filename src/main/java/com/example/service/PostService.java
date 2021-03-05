package com.example.service;

import com.example.dto.PostRequest;
import com.example.model.Post;
import com.example.model.User;
import com.example.repo.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Transactional
@Slf4j
@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;

    public List<Post> getPostsByUser(User user) {
        return postRepository.findAllById(Collections.singleton(user.getId()));
    }

    public void createPost(PostRequest postRequest) {
        postRepository.save(mapDtoToPost(postRequest));
    }

    private Post mapDtoToPost(PostRequest postRequest) {
        String[] dateInArray0 = postRequest.getStartTime().split(" "); // ex. 5-3-2021 10:30:00
        String[] startDateInArray = dateInArray0[0].split("-");
        String[] startTimeInArray = dateInArray0[1].split(":");
        LocalDate startLocalDate = LocalDate.of(Integer.parseInt(startDateInArray[0]),Integer.parseInt(startDateInArray[1]),Integer.parseInt(startDateInArray[2]));
        LocalTime startLocalTime = LocalTime.of(Integer.parseInt(startTimeInArray[0]), Integer.parseInt(startTimeInArray[1]));

        Post post = new Post();
        post.setUser(authService.getCurrentUser());
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setDistance(postRequest.getDistance());
        post.setStartTime(LocalDateTime.of(startLocalDate, startLocalTime));
        post.setEndTime(null);

        return post;
    }
}
