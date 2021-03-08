package com.example.mapper;

import com.example.dto.CommentResponse;
import com.example.model.Comment;
import com.example.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "text", source = "text")
    public abstract CommentResponse mapToDto(Comment comment);

}
