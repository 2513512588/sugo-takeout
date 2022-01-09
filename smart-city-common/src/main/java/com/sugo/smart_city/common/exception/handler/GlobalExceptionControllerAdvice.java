package com.sugo.smart_city.common.exception.handler;


import com.sugo.smart_city.common.enums.ResultCode;
import com.sugo.smart_city.common.exception.PermissionException;
import com.sugo.smart_city.common.exception.SysException;
import com.sugo.smart_city.common.util.Result;
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
//(basePackages = "com.smart_city.smartcity.controller")
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

    @ExceptionHandler(value = SysException.class)
    public Result handGlobException(SysException e){
        e.printStackTrace();
        return Result.error().message(e.getMessage());
    }

    @ExceptionHandler(value = PermissionException.class)
    public Result handGlobException(PermissionException e){
        return Result.of(ResultCode.FORBIDDEN).message(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result handGlobException(Exception e){
        e.printStackTrace();
        return Result.error().message(e.getMessage());
    }

}