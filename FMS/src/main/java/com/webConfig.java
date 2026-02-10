package com;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Match your API path
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500") // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}