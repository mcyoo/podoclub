package com.podoclub.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder
    public PostCreate(String title,String content){
        this.title = title;
        this.content = content;
    }
    //빌더의 장점
    // - 가독성에 좋다.(값 생성에 대한 유연함)
    // - 필요한 값만 받을 수 있다.
}
