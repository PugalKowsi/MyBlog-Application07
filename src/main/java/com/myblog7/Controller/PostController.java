//This Batch 27/March/2023 Class From Date=11/07/2023 17/07/2023 Post creation.
package com.myblog7.Controller;
import com.myblog7.Payload.PostDto;
import com.myblog7.Payload.PostResponse;
import com.myblog7.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
@RestController
@RequestMapping("api/posts")
public class PostController {
        PostService postService;
        public PostController(PostService postService) {this.postService = postService;
        }
        //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
        @PostMapping//12/07/2023
        public ResponseEntity<?>CreatePost(@Valid @RequestBody PostDto postDto, BindingResult result){
           if(result.hasErrors()){
               return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
           }
            return new ResponseEntity<>(postService.savePost(postDto), HttpStatus.CREATED);
        }
        //http://localhost:8080/api/posts?pageNo=0&pageSize=0&sortBy=title&sortDir=asc
        @GetMapping//20/07/2023 Pagination 20/07/2023
        public PostResponse getAllPosts(
        @RequestParam(value = "pageNo",defaultValue = "0",required = false)int pageNo,
        @RequestParam(value ="pageSize",defaultValue = "1",required = false)int pageSize,
        @RequestParam(value = "sortBy",defaultValue ="id",required = false)String sortBy,
        @RequestParam(value ="sortDir",defaultValue = "asc",required = false)String sortDir){
            PostResponse postResponse = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
            return postResponse;
        }
        //http://localhost:8080/api/posts/3
       @GetMapping("/{id}")
       public ResponseEntity<PostDto>getPostById(@PathVariable(name ="id")long id){
            return ResponseEntity.ok((PostDto)postService.getPostById(id));
       }
    //http://localhost:8080/api/posts/3
    @PreAuthorize("hasRole('ADMIN')")
       @PutMapping("/{id}")
       public ResponseEntity<PostDto>updatePostById
              (@RequestBody PostDto postDto,@PathVariable(name = "id")long id){
           PostDto postResponce=postService.updatePostById(postDto,id);
           return new ResponseEntity<>(postResponce,HttpStatus.OK);
       }
    //http://localhost:8080/api/posts/3
    @PreAuthorize("hasRole('ADMIN')")
       @DeleteMapping("/{id}")
        public ResponseEntity<String >deletePostById(@PathVariable(name ="id")long id){
            postService.deletePostById(id);
            return new ResponseEntity<>("Delete Successfully",HttpStatus.OK);
      }
    }
       //#12/07/2023
       //#20/07/2023 to 23/07/2023 Pagination and Sorting.
       //#24/07/2023 PostResponse class.
       //#25/07/2023 Spring Validation
       //#17/08/2023 Spring Security