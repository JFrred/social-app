package com.example.repo;

import com.example.model.Post;
import com.example.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByPost(Post post);
}
