package com.sugo.takeout.common.aspect.resolver;


import com.sugo.takeout.common.aspect.annotation.ParseLocation;
import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 注入经纬度信息
 * @author hehaoyang
 */
@Slf4j
public class ParseLocationHandlerMethodArgsResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ParseLocation.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        ParseLocation parseLocation = methodParameter.getParameterAnnotation(ParseLocation.class);
        assert parseLocation != null;
        String location = request.getParameter(parseLocation.value());
        Class<?> type = methodParameter.getParameter().getType();
        if (type == String.class){
            return StringUtil.formatLatLngStr(location);
        }else if (type == String[].class){
            return StringUtil.formatLatLng(location);
        }else {
            throw new SugoException("Location参数类型错误");
        }
    }
}
