package com.example.service;

import com.example.dto.VoteRequest;
import com.example.dto.VoteResponse;
import com.example.exception.SpringSocialAppException;
import com.example.exception.VoteSocialAppException;
import com.example.mapper.VoteMapper;
import com.example.model.Post;
import com.example.model.User;
import com.example.model.Vote;
import com.example.repo.PostRepository;
import com.example.repo.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Slf4j
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteMapper voteMapper;

    @Transactional
    public void vote(VoteRequest voteRequest) {
        Post post = postRepository.findById(voteRequest.getPostId()).orElseThrow(
                () -> new SpringSocialAppException("Post with id " + voteRequest.getPostId() + " does not exist."));
        Optional<Vote> userVoteOnPost = voteRepository.getUserVoteByPost(post, authService.getCurrentUser());

        // if user already voted, disable up/downvoting, depending what user already did in the past
        // if user tries to do same action again, delete his vote
        // (ex. user clicked upvote in past and now clicks again upvote to remove it (post) from liked his posts)
        if (userVoteOnPost.isPresent()) {
            Vote vote = userVoteOnPost.get();
            if (vote.getVoteDirection().equals(voteRequest.getVoteType().getDirection()))
                voteRepository.delete(vote);
            else {
                vote.setVoteDirection(voteRequest.getVoteType().getDirection());
                voteRepository.save(vote);
            }
        }
        else {
            // if user haven't voted yet, create new vote
            Vote vote = new Vote();
            vote.setPost(post);
            vote.setUser(authService.getCurrentUser());
            vote.setVoteDirection(voteRequest.getVoteType().getDirection());
            voteRepository.save(vote);

            int voteCount = post.getVoteCount() + vote.getVoteDirection();
            post.setVoteCount(voteCount);
            postRepository.save(post);
        }
    }

    public List<VoteResponse> getVotesByPostId(Long postId) {
        Post post = postRepository.findById(postId).get();
        return voteRepository.findAllByPost(post)
                .stream().map(voteMapper::mapToResponse)
                .collect(Collectors.toList());
    }
}


