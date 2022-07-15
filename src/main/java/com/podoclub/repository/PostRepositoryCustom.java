package com.podoclub.repository;

import com.podoclub.domain.Post;
import com.podoclub.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
