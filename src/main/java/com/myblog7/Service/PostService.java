package com.myblog7.Service;

import com.myblog7.Payload.PostDto;
import com.myblog7.Payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto savePost (PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePostById(PostDto postDto, long id);
    void deletePostById(long id);
}
