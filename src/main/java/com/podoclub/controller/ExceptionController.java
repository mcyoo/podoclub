package com.podoclub.controller;

import com.podoclub.exception.PodoclubException;
import com.podoclub.exception.PostNotFound;
import com.podoclub.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//에러 JSON 응답
@Slf4j
@ControllerAdvice
public class ExceptionController {

    /*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)// @Vaild 에러시.
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return response;
    }

    //이렇게 선언하면 무수히 많은 예외를 하나씩 선언해서 사용하기 때문에

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFound.class)
    public ErrorResponse postNotFound(PostNotFound e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("404")
                .message(e.getMessage())
                .build();

        return errorResponse;
    }
     */

    @ResponseBody
    @ExceptionHandler(PodoclubException.class)
    public ResponseEntity<ErrorResponse> podoclubException(PodoclubException e){
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }
}
