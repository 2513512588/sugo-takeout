package com.sugo.takeout.security.aspect;


import com.sugo.takeout.bean.model.User;
import com.sugo.takeout.common.exception.PermissionException;
import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import com.sugo.takeout.security.util.JwtTokenUtils;
import com.sugo.takeout.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 注入当前用户参数
 * @author hehaoyang
 */
@Slf4j
public class ParseUserHandlerMethodArgsResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    public ParseUserHandlerMethodArgsResolver(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ParseUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        ParseUser parseUser = methodParameter.getParameterAnnotation(ParseUser.class);
        assert parseUser != null;
        try {
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            assert request != null;
            String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
            if (StringUtils.isEmpty(token) && !parseUser.required()){
                return null;
            }
            String role = JwtTokenUtils.getUserRole(token);
            Class<?> type = methodParameter.getParameter().getType();
            Role roleParam = parseUser.value();
            if (Objects.equals(role, roleParam.getName())){
                if (type == User.class){
                    if (roleParam == Role.ROLE_USER){
                        Integer userId = JwtTokenUtils.getUserId(token);
                        return userService.getById(userId);
                    }else {
                        throw new SugoException("解析数据类型不一致");
                    }
                }else if (type == Integer.class){
                    return JwtTokenUtils.getAttachData(token, parseUser.value().getName());
                }
            }else {
                throw new SugoException("当前登录身份与接口不一致");
            }
        }catch (JwtException | NullPointerException | IllegalArgumentException e){
            if (parseUser.required()){
                throw new PermissionException(e.getMessage());
            }else {
                return null;
            }
        }
        throw new SugoException("没有匹配的参数类型");
    }
}
