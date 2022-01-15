package com.sugo.smart_city.security.aspect;


import com.sugo.smart_city.bean.model.User;
import com.sugo.smart_city.common.exception.PermissionException;
import com.sugo.smart_city.common.exception.SysException;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.security.enums.Role;
import com.sugo.smart_city.security.util.JwtTokenUtils;
import com.sugo.smart_city.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 注入当前用户参数
 * @author hehaoyang
 */
@Slf4j
public class ParseUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public ParseUserHandlerMethodArgumentResolver(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ParseUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        try {
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            ParseUser parseUser = methodParameter.getParameterAnnotation(ParseUser.class);
            assert request != null;
            String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
            Integer userId = JwtTokenUtils.getUserId(token);
            Class<?> type = methodParameter.getParameter().getType();
            assert parseUser != null;
            if (type == User.class){
                if (parseUser.value() == Role.ROLE_USER){
                    return userService.getById(userId);
                }else {
                    throw new SysException("解析数据类型不一致");
                }
            }else if (type == Integer.class){
                if (parseUser.value() == Role.ROLE_USER){
                    return userId;
                }else {
                    return JwtTokenUtils.getAttachData(token, parseUser.value().getName());
                }
            }
        }catch (JwtException | NullPointerException e){
            throw new PermissionException(e.getMessage());
        }
        return null;
    }
}
