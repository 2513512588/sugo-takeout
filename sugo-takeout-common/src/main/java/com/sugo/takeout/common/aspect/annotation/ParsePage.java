package com.sugo.takeout.common.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author hehaoyang
 * 解析分页信息
 */
@Documented
@Target({ PARAMETER })
@Retention(RUNTIME)
public @interface ParsePage {

    String pageNum() default "pageNum";
    String pageSize() default "pageSize";

}
