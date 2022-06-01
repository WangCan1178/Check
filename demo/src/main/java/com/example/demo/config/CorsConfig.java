package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域
        registry.addMapping("/**")
                .allowedOrigins("*")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //设置允许的方法
                .allowedMethods("*")
                .maxAge(3600);
    }
}
