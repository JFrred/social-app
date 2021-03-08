package com.example.service;

import com.example.dto.VoteDto;
import com.example.exception.SpringSocialAppException;
import com.example.model.Post;
import com.example.model.Vote;
import com.example.repo.PostRepository;
import com.example.repo.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@AllArgsConstructor
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(
                () -> new SpringSocialAppException("Post with id " + voteDto.getPostId() + " does not exist."));

        Vote vote = new Vote();
        vote.setPost(post);
        vote.setUser(authService.getCurrentUser());
        vote.setVoteType(voteDto.getVoteType());
        voteRepository.save(vote);

        post.setVoteCount(post.getVoteCount() + vote.getVoteType().getDirection());
        postRepository.save(post);

        System.out.println("voteType direction = " + voteDto.getVoteType().getDirection());
        System.out.println(">> VOTESERVICE >> vote() >> post: " + post);
    }
}


