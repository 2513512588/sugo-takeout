package com.sugo.takeout.common.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author hehaoyang
 * 接收单个json数据
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestSingleParam {

    String value();
    boolean required() default true;

}
