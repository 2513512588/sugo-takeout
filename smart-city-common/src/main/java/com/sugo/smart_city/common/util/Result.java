package com.sugo.smart_city.common.util;


import com.sugo.smart_city.common.enums.ResultCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一数据返回格式
 */
@Data
public class Result {



    private Integer code;
    private String message;
    private Boolean success;
    private Map<String, Object> data = new HashMap<>();

    private Result() {

    }

    public static Result ok(){
        return of(ResultCode.SUCCESS);
    }

    public static Result error(){
        return of(ResultCode.FAILED);
    }

    public static Result of(ResultCode resultCodeEnum){
        Result result = new Result();
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        result.setSuccess(resultCodeEnum.getSuccess());
        return result;
    }

    public static Result auto(boolean auto) {
        return of(auto ? ResultCode.SUCCESS : ResultCode.FAILED);
    }

    public Result code(Integer code){
        setCode(code);
        return this;
    }

    public Result message(String message){
        setMessage(message);
        return this;
    }

    public Result data(String key, Object value){
        data.put(key, value);
        return this;
    }
}
