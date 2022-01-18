package com.sugo.smart_city.security.annotation;






import com.sugo.smart_city.security.enums.Role;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author hehaoyang
 * 用于注入当前用户参数
 */
@Documented
@Target({ PARAMETER })
@Retention(RUNTIME)
public @interface ParseUser {

    Role value() default Role.ROLE_USER;

    boolean required() default true;

}
