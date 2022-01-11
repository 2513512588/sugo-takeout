package com.sugo.smart_city.common.aspect;


import com.alibaba.fastjson.JSONObject;
import com.sugo.smart_city.common.aspect.annotation.RequestSingleParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

@Aspect
@Component
public class ParseParamAspect {

    @Pointcut("@annotation(com.sugo.smart_city.common.aspect.annotation.ParseParam)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int rd;
        while ((rd = reader.read(buf)) != -1) {
            sb.append(buf, 0, rd);
        }
        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i <parameters.length; i++) {
            Annotation[] annotations = parameters[i].getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof RequestSingleParam){
                    args[i] = jsonObject.get(((RequestSingleParam) annotation).value());
                }
            }
        }
        return proceedingJoinPoint.proceed(args);
    }

}
