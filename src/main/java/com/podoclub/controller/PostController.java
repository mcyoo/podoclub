package com.podoclub.controller;

import com.podoclub.domain.Post;
import com.podoclub.request.PostCreate;
import com.podoclub.response.PostResponse;
import com.podoclub.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    // SSR -> jsp, thymeleaf, mustache, freemarker
    // SPA -> vue(nust), react(next)

    //HTTP Method
    //GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    //글 등록
    //POST

    private final PostService postService;

    @PostMapping("/posts")
    public Map post(@RequestBody @Valid PostCreate request) {
        // Case 1 : 저장한 데이터 Entity -> response로 응답하기
        // Case 2 : 저장한 데이터의 primary id 만 응답
        //          Client 에서는 수신한 id를 가지고 글 조회 API 를 통해서 데이터를 수신 받음
        // Case 3 : 응답이 필요 없음 (클라이언트에서 모든 POST 데이터 context를 잘 관리함)
        // Bad Case : 서버에서 반드시 이렇게 할 겁니다. fix 하는 경우
        //            서버에서 유연하게 대응하는게 좋습니다.
        //            한번에 일괄적으로 처리되는 케이스가 없습니다.
        // 일단 요청 응답을 ID 로 반환, 기획에 따라 다르다.
        Long postId = postService.write(request);
        return Map.of("postId",postId);

        //데이터를 검증하는 이유
        // 1. 클라이언트 개발자가 깜빡할 수 있다. 실수로 값을 안 보낼 수 있다.
        // 2. 클라이언트 버그로 값이 누락
        // 3. 외부에 임의로 값을 조작할 수 있다.
        // 4. DB에 값을 저장할때 오류가 발생 할 수 있기 때문에

        /*
        //첫번째 검증

        String title = params.getTitle();
        String content = params.getContent();

        //이렇게 검증을 하게 되면 검증해야할 부분이 많다.
        // 검증할 데이터가 많아지면 복잡해지고 번거로워진다.
        // 그래서 검증해주는 @NotNull 과 @Valid 를 써서 쉽게 검증한다.
        if(title == null || title.equals("")){
            throw new Exception("타이틀 값이 없어요!");
        }
        log.info("params={}",params.toString());
        */

        /*
        //두번째 BindingResult result 파라미터를 추가해서 예외처리를 받아서 처리

        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField();
            String errorMessage = firstFieldError.getDefaultMessage();// 에러 메세지

            Map<String,String> error = new HashMap<>();
            error.put(fieldName,errorMessage);
            return error;

        }
        */
    }
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){
        return postService.get(postId);
    }



    @GetMapping("/posts")
    public List<PostResponse> getList(){
        return postService.getList();
    }

}
