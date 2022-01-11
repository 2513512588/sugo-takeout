package com.sugo.smart_city.api.config;


import com.sugo.smart_city.common.aspect.resolver.ParsePageHandlerMethodArgumentResolver;
import com.sugo.smart_city.security.aspect.ParseUserHandlerMethodArgumentResolver;
import com.sugo.smart_city.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hehaoyang
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private UserService userService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Content-Type")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")
                ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(new ParsePageHandlerMethodArgumentResolver());
        resolvers.add(new ParseUserHandlerMethodArgumentResolver(userService));
    }
}
