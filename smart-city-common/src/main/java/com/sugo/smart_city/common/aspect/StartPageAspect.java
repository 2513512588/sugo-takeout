package com.sugo.smart_city.common.aspect;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.smart_city.common.exception.SysException;
import com.sugo.smart_city.common.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StartPageAspect {

    @Pointcut("@annotation(com.sugo.smart_city.common.aspect.annotation.StartPage)")
    public void pointCut(){
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object proceed = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        if (proceed.getClass() == Page.class){
            Page<?> page = (Page<?>) proceed;
            return Result.ok()
                    .data("rows", page.getRecords())
                    .data("total", page.getTotal())
                    .data("pages", page.getPages());
        }else {
            throw new SysException("分页方法返回值应该为com.baomidou.mybatisplus.extension.plugins.pagination.Page");
        }
    }

}
