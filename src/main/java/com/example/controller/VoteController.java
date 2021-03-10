package com.example.controller;

import com.example.dto.VoteRequest;
import com.example.dto.VoteResponse;
import com.example.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
@AllArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteRequest voteDto) {
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VoteResponse>> getAllPostVotes(@RequestParam Long postId) {
        return new ResponseEntity<>(voteService.getVotesByPostId(postId), HttpStatus.OK);
    }

}
