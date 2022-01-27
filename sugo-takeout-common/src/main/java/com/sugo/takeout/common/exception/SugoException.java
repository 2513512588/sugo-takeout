package com.sugo.takeout.common.exception;

import com.sugo.takeout.common.enums.ResultCode;
import lombok.Getter;

@Getter
public class SugoException extends RuntimeException{

    private final ResultCode resultCode;

    public SugoException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILED;
    }

    public SugoException(String message, ResultCode resultCode) {
        super(message);
        this.resultCode = resultCode;
    }
}
