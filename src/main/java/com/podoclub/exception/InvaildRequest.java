package com.podoclub.exception;

import lombok.Getter;

@Getter
public class InvaildRequest extends PodoclubException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvaildRequest() { super(MESSAGE);}

    public InvaildRequest(String fieldName,String message){
        super(MESSAGE);
        addValidation(fieldName,message);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }

}
