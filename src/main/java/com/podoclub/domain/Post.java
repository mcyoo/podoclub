package com.podoclub.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title,String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle(){
        //서비스의 정책을 넣지마세요!! 절대!!
        return this.title;//.substring(0,10);
    }
}
