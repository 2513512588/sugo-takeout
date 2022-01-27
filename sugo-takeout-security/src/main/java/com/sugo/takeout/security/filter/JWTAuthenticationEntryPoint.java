package com.sugo.takeout.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugo.takeout.common.enums.ResultCode;
import com.sugo.takeout.common.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 鉴权被拒
 */
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(Result.of(ResultCode.FORBIDDEN).message(authException.getMessage())));
    }
}
