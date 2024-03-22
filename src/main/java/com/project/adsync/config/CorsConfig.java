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
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/adSync.api/advertisement/**")
                        //.allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://www.adsynclk.xyz")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                corsRegistry.addMapping("/adSync.api/user/**")
                        //.allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://www.adsynclk.xyz")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                corsRegistry.addMapping("/adSync.api/admin/**")
                        //.allowedOrigins("http://localhost:3000")
                        .allowedOrigins("http://www.adsynclk.xyz")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
                corsRegistry.addMapping("/adSync.api/cloudStorage/**") // Add this line
                        .allowedOrigins("http://www.adsynclk.xyz")
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow only POST method for uploads
                        .allowedHeaders("*");
            }


        };

    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                corsRegistry.addMapping("/***")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }*/
    }
}
