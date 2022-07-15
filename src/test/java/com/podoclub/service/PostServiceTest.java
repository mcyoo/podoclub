package com.podoclub.service;

import com.podoclub.domain.Post;
import com.podoclub.repository.PostRepository;
import com.podoclub.request.PostCreate;
import com.podoclub.request.PostEdit;
import com.podoclub.request.PostSearch;
import com.podoclub.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.DESC;

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
        PostResponse response = postService.get(requestPost.getId());

        //then
        assertNotNull(response);
        assertEquals("foo",response.getTitle());
        assertEquals("bar",response.getContent());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void Test3(){

        //given
        List<Post> requestPosts = IntStream.range(1,31)
                .mapToObj(i-> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        /*
        Long postId = 1L;

        postRepository.saveAll(List.of(
                Post.builder()
                        .title("foo1")
                        .content("bar1")
                        .build(),
                Post.builder()
                        .title("foo2")
                        .content("bar2")
                        .build()

        ));
         */

        //Pageable pageable = PageRequest.of(0,5, DESC,"id");

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        //when
        List<PostResponse> posts = postService.getList(postSearch);

        //then
        assertEquals(10L,posts.size());
        assertEquals("제목 30",posts.get(0).getTitle());
        assertEquals("제목 26",posts.get(4).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void Test4(){

        //given
        Post post = Post.builder()
                .title("제석")
                .content("짱")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("젝슨")
                .content("짱")
                .build();
        //when
        postService.edit(post.getId(),postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                        .orElseThrow(()->new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("젝슨",changedPost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void Test5(){

        //given
        Post post = Post.builder()
                .title("제석")
                .content("짱짱맨")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("젝슨")
                .content("짱")
                .build();
        //when
        postService.edit(post.getId(),postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(()->new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("짱",changedPost.getContent());
    }
}