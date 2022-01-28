package com.sugo.takeout.api.config;


import com.sugo.takeout.common.aspect.resolver.ParsePageHandlerMethodArgumentResolver;
import com.sugo.takeout.security.aspect.ParseUserHandlerMethodArgumentResolver;
import com.sugo.takeout.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(new ParsePageHandlerMethodArgumentResolver());
        resolvers.add(new ParseUserHandlerMethodArgumentResolver(userService));
    }

    @Bean
    public FormContentFilter httpPutFormContentFilter() {
        return new FormContentFilter();
    }
}
