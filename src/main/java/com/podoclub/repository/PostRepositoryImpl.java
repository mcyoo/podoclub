package com.podoclub.repository;

import com.podoclub.domain.Post;
import com.podoclub.domain.QPost;
import com.podoclub.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.support.QPostgreSQL9Dialect;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.podoclub.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
