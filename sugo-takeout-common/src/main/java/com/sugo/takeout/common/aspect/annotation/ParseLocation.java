package com.sugo.takeout.common.aspect.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 解析经纬度信息
 * @author hehaoyang
 */
@Documented
@Target({ PARAMETER })
@Retention(RUNTIME)
public @interface ParseLocation {

    String value() default "location";

}
