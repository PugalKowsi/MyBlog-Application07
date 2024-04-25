package com.myblog7.Controller;

import com.myblog7.Payload.CommentDto;
import com.myblog7.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //Http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(@PathVariable(value = "postId")long postId,
                                                   @RequestBody CommentDto commentDto){
    return new ResponseEntity<>( commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
    //Http://localhost:8080/api/posts/
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto>getCommentsByPostId(@PathVariable(value = "postId")Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    //Http://localhost:8080/api/posts/1/comments/2
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto GetCommentsById(@PathVariable(value = "postId")long postId,
                                           @PathVariable(value = "commentId")long commentId){
        return commentService.getCommentById(postId,commentId);
    }
    //Http://localhost:8080/api/comments
    @GetMapping("/comments")
    public List<CommentDto>getAllComments(){
       return commentService.getAllComments();
    }

    //Http://localhost:8080/api/posts/1/comments/2
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto>updateCommentById(@PathVariable(value = "commentId")long commentId,
              @PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto){
        System.out.println(commentDto.getEmail());
    return new ResponseEntity<>(commentService.updateCommentById(commentId,postId,commentDto),HttpStatus.OK);}

    //Http://localhost:8080/api/posts/1/comments/2
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId")long postId,
                             @PathVariable(value = "commentId")long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<String>("Comment was Deleted",HttpStatus.OK);
    }
}
