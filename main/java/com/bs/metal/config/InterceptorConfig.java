package com.bs.metal.config;

import com.bs.metal.Interception.CheckTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置jwt拦截器
 * @auther Bongo
 * @create 2023/4/13 0:23
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private CheckTokenInterceptor checkTokenInterception;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(checkTokenInterception)
                .addPathPatterns("/*")
                .excludePathPatterns("/user/**");
    }
}
