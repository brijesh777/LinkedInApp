package com.codingshuttle.linkedin.posts_service.service;

import com.codingshuttle.linkedin.posts_service.entity.PostLike;
import com.codingshuttle.linkedin.posts_service.exception.BadRequestException;
import com.codingshuttle.linkedin.posts_service.exception.ResourceNotFoundException;
import com.codingshuttle.linkedin.posts_service.repository.PostLikeRepository;
import com.codingshuttle.linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {


    public void unlikePost(Long postId, long l) {
    }

    public void likePost(Long postId, long l) {
    }
}
