package com.podoclub.service;

import com.podoclub.domain.Post;
import com.podoclub.domain.PostEditor;
import com.podoclub.repository.PostRepository;
import com.podoclub.request.PostCreate;
import com.podoclub.request.PostEdit;
import com.podoclub.request.PostSearch;
import com.podoclub.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

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

    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    //글이 너무 많은 경우 -> 비용이 너무 많이 든다.
    //글이 100,000,000 개인 경우 -> DB가 뻗을 수 있다.
    //DB -> 애플리케이션 서버로 전달되는 시간 ,트래픽 비용 발생
    public List<PostResponse> getList(PostSearch postSearch){
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                        .content(postEdit.getContent())
                                .build();

        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 글입니다."));

        postRepository.delete(post);
    }
}
