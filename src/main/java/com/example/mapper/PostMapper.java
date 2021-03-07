package com.example.mapper;

import com.example.dto.PostRequest;
import com.example.model.Post;
import com.example.model.User;
import com.example.repo.CommentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "title", source = "postRequest.title")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    public abstract Post mapDto(PostRequest postRequest, User user);

    Integer commentCount(Post post) {
        return commentRepository.findCommentsByPost(post).size();
    }

//    @Mapping()
//    PostRequest mapToDto(Post post);
}
