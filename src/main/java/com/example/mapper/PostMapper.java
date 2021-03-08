package com.example.mapper;

import com.example.dto.PostRequest;
import com.example.model.Post;
import com.example.model.PostResponse;
import com.example.model.User;
import com.example.repo.CommentRepository;
import com.example.repo.VoteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "title", source = "postRequest.title")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    public abstract Post mapDto(PostRequest postRequest, User user);

    @Mapping(target = "postId", source = "id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "voteCount", expression = "java(voteCount(post))")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    public abstract PostResponse mapToResponse(Post post);

    Integer voteCount(Post post) {
        return voteRepository.findAllByPost(post).size();
    }

    Integer commentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

}
