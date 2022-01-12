package com.sugo.smart_city.common.util;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.smart_city.common.enums.ResultCode;
import com.sugo.smart_city.common.exception.SysException;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一数据返回格式
 */
@Data
public class Result {



    private Integer code;
    private String message;
    private Boolean success;
    private Object data = new HashMap<String, Object>();

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
        if (data instanceof Map){
            Map<String, Object> map = (Map<String, Object>) data;
            map.put(key, value);
        }else {
            throw new SysException("Result.data数据类型错误");
        }
        return this;
    }

    public Result data(Object value){
        data = value;
        return this;
    }

    public Result list(List<?> list){
        data("rows", list);
        return this;
    }

    public Result pageList(IPage<?> page, List<?> list){
        data("rows", list).
        data("total", page.getTotal()).
        data("pages", page.getPages());
        return this;
    }

    public Result pageList(IPage<?> page){
        pageList(page, page.getRecords());
        return this;
    }


}
