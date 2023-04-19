package com.example.blogapi.service.impl;

import com.example.blogapi.exception.ResourceNotFound;
import com.example.blogapi.model.Post;
import com.example.blogapi.payload.PostDto;
import com.example.blogapi.payload.PostResponse;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createdPost(PostDto postDto) {

        //convert dto to entities
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post newPost = postRepository.save(post);

        //converting entity to dto
        PostDto postDtoResponse = mapToDto(newPost);

        return postDtoResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo,int pageSize,String sortBy) {

        PageRequest pageable =  PageRequest.of(pageNo,pageSize, Sort.by(sortBy));

        Page<Post> postList = postRepository.findAll(pageable);

        List<Post> postList1 = postList.getContent();

        List<PostDto> content = postList1.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postList.getNumber());
        postResponse.setPageSize(postList.getSize());
        postResponse.setTotalElement(postList.getTotalElements());
        postResponse.setTotalPage(postList.getTotalPages());
        postResponse.setLast(postList.isLast() );

        return postResponse;


    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new
                ResourceNotFound("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new
                ResourceNotFound("Post","id",id));

        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new
                ResourceNotFound("Post","id",id));
        postRepository.delete(post);

    }

    private PostDto mapToDto(Post post){
        PostDto postDto = modelMapper.map(post,PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());
//        postDto.setId(post.getId());
        return postDto;

    }

    private Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto,Post.class);

//        Post post =  new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }


}
