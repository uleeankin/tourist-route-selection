package com.uleeankin.touristrouteselection.security.config;

import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/admin").setViewName("admin/adminMain");
        registry.addViewController("/moderator").setViewName("moderator/moderatorMain");
        registry.addViewController("/tourist").setViewName("tourist/touristMain");
        registry.addViewController("/register").setViewName("register");
    }
}
