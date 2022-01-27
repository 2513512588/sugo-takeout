package com.sugo.takeout.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hehaoyang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Delay {

    /**
     * 访问限制默认2000ms/次
     */
    int value() default 2000;

}
