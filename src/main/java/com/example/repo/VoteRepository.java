package com.example.repo;

import com.example.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
