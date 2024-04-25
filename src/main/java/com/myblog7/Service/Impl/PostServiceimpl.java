package com.myblog7.Service.Impl;

import com.myblog7.Entity.Post;
import com.myblog7.Exception.ResourceNotFoundException;
import com.myblog7.Payload.PostDto;
import com.myblog7.Payload.PostResponse;
import com.myblog7.Repository.PostRepository;
import com.myblog7.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceimpl implements PostService {
    PostRepository postRepository;
    private ModelMapper modelMapper;
    //Here two type of library one is Internal and External (ModelMapper is external library)

    public PostServiceimpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }
    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post saved = postRepository.save(post);
        PostDto Dto = mapToDto(saved);
        return Dto;
    }
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase
       (Sort.Direction.ASC.name())?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

         Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<Post> pagePosts =postRepository.findAll(pageable);

        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalPage(pagePosts.getTotalPages());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLast(pagePosts.isLast());
        return postResponse;
    }
    @Override
    public PostDto getPostById(long id) {
       Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }
    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",id));

      //  Post post1 = modelMapper.map(postDto, Post.class);
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        postRepository.save(post);
        return mapToDto(post);
    }
    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
    private PostDto mapToDto(Post post) {
       PostDto Dto= modelMapper.map(post,PostDto.class);
        return Dto;
    }
}
