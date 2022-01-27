package com.sugo.takeout.common.exception.handler;


import com.sugo.takeout.common.enums.ResultCode;
import com.sugo.takeout.common.exception.PermissionException;
import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
//(basePackages = "com.takeout.smartcity.controller")
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(value= {MethodArgumentNotValidException.class , BindException.class})
    public Result handleVaildException(Exception e){
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException)e).getBindingResult();
        }
        Map<String,String> errorMap = new HashMap<>(16);
        assert bindingResult != null;
        bindingResult.getFieldErrors().forEach((fieldError)->
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage())
        );
        return Result.of(ResultCode.VALIDATE_FAILED).data("errorMap", errorMap);
    }

    @ExceptionHandler(value = SugoException.class)
    public Result handGlobException(SugoException e){
        e.printStackTrace();
        return Result.of(e.getResultCode()).message(e.getMessage());
    }

    @ExceptionHandler(value = {PermissionException.class})
    public Result handGlobException(PermissionException e){
        return Result.of(ResultCode.FORBIDDEN).message("身份校验失败");
    }

    @ExceptionHandler(value = Exception.class)
    public Result handGlobException(Exception e){
        e.printStackTrace();
        return Result.error().message(e.getMessage());
    }

}