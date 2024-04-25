package com.myblog7.Service;

import com.myblog7.Entity.Comment;
import com.myblog7.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto>getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    List<CommentDto> getAllComments();
    void deleteCommentById(long postId, long commentId);
    CommentDto updateCommentById(long commentId,long postId, CommentDto commentDto);
}
