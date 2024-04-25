package com.myblog7.Service.Impl;
import com.myblog7.Entity.Comment;
import com.myblog7.Entity.Post;
import com.myblog7.Exception.BlogAPIException;
import com.myblog7.Exception.ResourceNotFoundException;
import com.myblog7.Payload.CommentDto;
import com.myblog7.Repository.CommentRepository;
import com.myblog7.Repository.PostRepository;
import com.myblog7.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.stream.Collectors;

@Service
public class CommentServiceimpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceimpl(CommentRepository commentRepository,PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
        Comment saved = commentRepository.save(comment);
        CommentDto Dto = mapToDto(saved);
        return Dto;
    }
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //This will be Check post are exes est
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",postId));
        //Than get comments entity based on Post Id
        List<Comment> comments = commentRepository.findByPostId(postId);
        //Using stream API convertion of Entity to Dto
        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
       Post post=postRepository.findById(postId).orElseThrow(
               ()->new ResourceNotFoundException("post","id",postId));

       Comment comments=commentRepository.findById(commentId).orElseThrow(
               ()->new ResourceNotFoundException("comment","id",commentId));

       if(!comments.getPost().getId().equals(post.getId())){
       throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment dose not belong post id");
        }

        CommentDto commentDto = mapToDto(comments);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> dtos = comments.stream().map(comment-> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment dose not belong post id");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
public CommentDto updateCommentById(long commentId,long postId, CommentDto commentDto) {
Post post = postRepository.findById(postId).orElseThrow(
        () -> new ResourceNotFoundException("post", "id", postId));

Comment comment=commentRepository.findById(commentId).orElseThrow(
        ()->new ResourceNotFoundException("comment","id",commentId));

if(!comment.getPost().getId().equals(post.getId())){
throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment dose not belong post id");
}
        Comment comment1=mapToEntity(commentDto);

        Comment updateComment1 = commentRepository.save(comment1);

        CommentDto commentDto1 = mapToDto(updateComment1);
        return commentDto1;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
    private CommentDto mapToDto(Comment comment){
        CommentDto Dto = modelMapper.map(comment, CommentDto.class);
        return Dto;
    }
}