package com.example.mapper;

import com.example.dto.VoteResponse;
import com.example.model.Vote;
import com.example.model.VoteType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class VoteMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "voteType", expression = "java(getVoteNameFromValue(vote.getVoteDirection()))")
    public abstract VoteResponse mapToResponse(Vote vote);

    VoteType getVoteNameFromValue(Integer voteDirection) {
        if (voteDirection == 1)
            return VoteType.UPVOTE;
        return VoteType.DOWNVOTE;
    }
}
