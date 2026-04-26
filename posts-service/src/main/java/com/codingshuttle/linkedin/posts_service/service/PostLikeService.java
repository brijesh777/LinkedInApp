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

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;


    public void unlikePost(Long postId, long userId) {
        log.info("Attempting to unlike the post with id: {}", postId);
        boolean exists = postsRepository.existsById(postId);
        if (!exists) throw new ResourceNotFoundException("Post not found with id: " + postId);

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (!alreadyLiked) throw new BadRequestException("Cannot unlike the post which is not liked.");

        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        log.info("Post with id: {} unliked successfully", postId);
    }

    public void likePost(Long postId, long userId) {
        log.info("Attempting to like post with id {} ", postId);

        boolean exists = postLikeRepository.existsById(postId);
        if (!exists) throw new ResourceNotFoundException("Post not found with id: " + postId);

        boolean alreadyLiked = postLikeRepository.existsById(postId);
        if (!alreadyLiked) throw new BadRequestException("Post already liked with id: " + postId);


        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Post with id: {} liked successfully", postId);
    }


}
