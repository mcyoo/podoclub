package com.podoclub.service;

import com.podoclub.domain.Post;
import com.podoclub.repository.PostRepository;
import com.podoclub.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Long write(PostCreate postCreate){
        //postCreate => Entity

        Post post = Post.builder()
                        .title(postCreate.getTitle())
                        .content(postCreate.getContent())
                        .build();

        postRepository.save(post);

        return post.getId();
    }

    public Post get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));

        return post;
    }
}