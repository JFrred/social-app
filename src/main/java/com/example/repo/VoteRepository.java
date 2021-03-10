package com.example.repo;

import com.example.model.Post;
import com.example.model.User;
import com.example.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByPost(Post post);
    List<Vote> findAllByUser(User user);

    @Query("SELECT v FROM Vote v WHERE v.post=?1 AND v.user=?2")
    Optional<Vote> getUserVoteByPost(Post post, User user);
}
