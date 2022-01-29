package com.sugo.takeout.common.config;

import com.sugo.takeout.common.aspect.resolver.ParseLocationHandlerMethodArgsResolver;
import com.sugo.takeout.common.aspect.resolver.ParsePageHandlerMethodArgsResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author hehaoyang
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("Content-Type", "*")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE");
    }


    @Bean
    public FormContentFilter httpPutFormContentFilter() {
        return new FormContentFilter();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(new ParsePageHandlerMethodArgsResolver());
        resolvers.add(new ParseLocationHandlerMethodArgsResolver());
    }

}
