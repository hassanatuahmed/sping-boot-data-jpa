package com.example.blogapi.controller;

import com.example.blogapi.payload.PostDto;
import com.example.blogapi.payload.PostResponse;
import com.example.blogapi.service.PostService;
import com.example.blogapi.utils.AppConstant;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create-post")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody   PostDto postDto){
        return new ResponseEntity<>(postService.createdPost(postDto), HttpStatus.CREATED);

    }

    @GetMapping("/")
    public PostResponse getPost(@RequestParam(value = "pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                @RequestParam(value = "pageSize",defaultValue = AppConstant.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                                @RequestParam(value = "sortBy",defaultValue = AppConstant.DEFAULT_SORT_BY,required = false) String sortBy
                                ){
        return postService.getAllPost(pageNo,pageSize,sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById( @PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody  PostDto postDto, @PathVariable(name = "id") Long id){
       PostDto postDtoResponse = postService.updatePost(postDto,id);

       return  new ResponseEntity<>(postDtoResponse,HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMIN')")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){

        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);


    }


}
