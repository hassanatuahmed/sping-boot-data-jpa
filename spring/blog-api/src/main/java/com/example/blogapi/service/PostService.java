package com.example.blogapi.service;

import com.example.blogapi.payload.PostDto;
import com.example.blogapi.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createdPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto,Long id);

    void  deletePost(long id);


}
