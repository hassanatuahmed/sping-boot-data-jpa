package com.example.blogapi.service.impl;

import com.example.blogapi.exception.BlogApiException;
import com.example.blogapi.exception.ResourceNotFound;
import com.example.blogapi.model.Comment;
import com.example.blogapi.model.Post;
import com.example.blogapi.payload.CommentDto;
import com.example.blogapi.repository.CommentRepository;
import com.example.blogapi.repository.PostRepository;
import com.example.blogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
//        commentDto.setId(comment.getId());
        return commentDto;

    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());


        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "id", postId));
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        CommentDto responseDto = mapToDto(newComment);

        return responseDto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //get comment by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

        //converting comment entity to comment dto
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFound("comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {

            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");

        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not exist");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);


        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not exist");
        }

        commentRepository.delete(comment);
    }
}
