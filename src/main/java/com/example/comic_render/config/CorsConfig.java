package com.example.comic_render.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")   // Chỉ cho phép domain frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Khai báo chi tiết phương thức
                .allowedHeaders("*")                         // Cho phép tất cả các header
                .allowCredentials(true)                      // Nếu cần gửi cookie/authorization header
                .maxAge(3600);                               // Thời gian cache kết quả preflight request
    }
}


