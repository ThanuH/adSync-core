package com.project.adsync.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/advertisement/**")
                        //.allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://34.72.244.127:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/user/**")
                        //.allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://34.72.244.127:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/admin/**")
                        //.allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://34.72.244.127:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }


        };
    }
}
