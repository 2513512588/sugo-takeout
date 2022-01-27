package com.sugo.takeout.common.enums;


import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 *
 * @author macro
 * @date 2019/4/19
 *
 * @date 2021/12/30
 * @author hehaoyang
 */

@Getter
public enum ResultCode {

    /**
     * 枚举常用返回状态
     */
    SUCCESS(200, "操作成功", true),
    FAILED(500, "操作失败", false),
    VALIDATE_FAILED(400, "参数检验失败", false),
    UNAUTHORIZED(401, "暂未登录或session已经过期", false),
    FORBIDDEN(403, "没有相关权限", false);

    private final Integer code;
    private final String message;
    private final Boolean success;


    ResultCode(Integer code, String message, Boolean success) {
         this.code = code;
         this.message = message;
         this.success = success;
    }

}
