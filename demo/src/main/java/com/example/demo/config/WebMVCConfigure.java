package com.example.demo.config;

import cn.hutool.core.net.url.UrlPath;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import sun.plugin2.message.helper.URLHelper;

import java.nio.charset.StandardCharsets;

@Configuration
public class WebMVCConfigure implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlHelper = new UrlPathHelper();
        urlHelper.setUrlDecode(false);
        urlHelper.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configurer.setUrlPathHelper(urlHelper);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pics/**")
                .addResourceLocations("file:/E:/git/Check/demo/src/main/resources/static/pics/");
    }
}
