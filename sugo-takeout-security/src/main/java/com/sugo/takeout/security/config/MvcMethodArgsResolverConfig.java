package com.sugo.takeout.security.config;

import com.sugo.takeout.security.aspect.ParseUserHandlerMethodArgsResolver;
import com.sugo.takeout.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hehaoyang
 */
@Configuration
public class MvcMethodArgsResolverConfig implements WebMvcConfigurer {

    @Resource
    private UserService userService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(new ParseUserHandlerMethodArgsResolver(userService));
    }

}
