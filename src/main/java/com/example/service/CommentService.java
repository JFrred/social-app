package com.example.service;

import com.example.dto.CommentRequest;
import com.example.dto.CommentResponse;
import com.example.exception.SpringSocialAppException;
import com.example.mapper.CommentMapper;
import com.example.model.Comment;
import com.example.model.Post;
import com.example.repo.CommentRepository;
import com.example.repo.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public List<CommentResponse> getPostComments(Long postId) {
        Post post = postRepository.findById(postId).get();

        return commentRepository.findAllByPost(post)
                .stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public void createComment(Long postId, CommentRequest commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new SpringSocialAppException(String.format("Post with id %d does not exist.", postId)));

        Comment comment = new Comment();
        comment.setCreatedDate(Instant.now());
        comment.setText(commentRequest.getText());
        comment.setPost(post);
        comment.setUser(authService.getCurrentUser());
        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount()+1);
        postRepository.save(post);
    }
}
