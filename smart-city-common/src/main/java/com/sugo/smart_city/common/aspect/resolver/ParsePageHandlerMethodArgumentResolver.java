package com.sugo.smart_city.common.aspect.resolver;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.smart_city.common.aspect.annotation.ParsePage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 注入分页信息
 * @author hehaoyang
 */
@Slf4j
public class ParsePageHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ParsePage.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        assert request != null;
        ParsePage parsePage = methodParameter.getParameterAnnotation(ParsePage.class);
        assert parsePage != null;
        String pageNum = request.getParameter(parsePage.pageNum());
        String pageSize = request.getParameter(parsePage.pageSize());
        if (StringUtils.isEmpty(pageNum)){
            pageNum = "1";
        }
        if (StringUtils.isEmpty(pageSize)){
            pageSize = "10";
        }
        return new Page<>(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }
}
