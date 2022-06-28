package com.podoclub.service;

import com.podoclub.domain.Post;
import com.podoclub.repository.PostRepository;
import com.podoclub.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void Test1(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목 입니다.")
                .content("내용 입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1,postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목 입니다.",post.getTitle());
        assertEquals("내용 입니다.",post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void Test2(){
        //given
        Long postId = 1L;

        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        //when
        Post post = postService.get(requestPost.getId());

        //then
        assertNotNull(post);
        assertEquals("foo",post.getTitle());
        assertEquals("bar",post.getContent());
    }
}