package com.sugo.smart_city.common.aspect;


import com.alibaba.fastjson.JSONObject;
import com.sugo.smart_city.common.aspect.annotation.RequestSingleParam;
import com.sugo.smart_city.common.exception.SysException;
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
        Object[] args = proceedingJoinPoint.getArgs();
        if (jsonObject != null){
            Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i <parameters.length; i++) {
                Annotation[] annotations = parameters[i].getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof RequestSingleParam){
                        String key = ((RequestSingleParam) annotation).value();
                        if (jsonObject.containsKey(key)){
                            args[i] = jsonObject.get(key);
                        }else {
                            throw new SysException(String.format("缺少参数%s", key));
                        }
                    }
                }
            }
        }else {
            throw new SysException("请传入json参数");
        }
        return proceedingJoinPoint.proceed(args);
    }

}
