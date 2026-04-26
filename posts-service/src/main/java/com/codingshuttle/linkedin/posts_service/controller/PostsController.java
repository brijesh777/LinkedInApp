package com.codingshuttle.linkedin.posts_service.controller;

import com.codingshuttle.linkedin.posts_service.dto.PostDto;
import com.codingshuttle.linkedin.posts_service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, Long userId) {
        PostDto createdPost = postsService.createdPost(postDto, userId);
        PostDto createPost =postsService.createdPost(postDto, 1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }
}
