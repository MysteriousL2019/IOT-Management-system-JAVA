package com.example.iotmanager.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter   {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /**
         * 1.与访问路径
         * 2.请求来源
         * 3.方法
         * 4.允许携带token
         * 5.最大响应时间
         */


//       /**表示访问的任何东西都允许你跨域,8080端口需要更改（可能），maxAge是最大的耗时时间
        registry.addMapping("/**")
                .allowedOrigins("Http://localhost:8080","null")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
