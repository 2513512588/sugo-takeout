package com.sugo.smart_city.common.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD })
@Retention(RUNTIME)
public @interface ParseParam {

}
