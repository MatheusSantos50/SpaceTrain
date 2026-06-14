package com.siteoob.spacetrain.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Autowired
    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**", "/favicon.ico", "/uploads/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadsPath = java.nio.file.Paths.get(System.getProperty("user.dir"), "uploads").toAbsolutePath().toUri().toString();
        if (!uploadsPath.endsWith("/")) {
            uploadsPath += "/";
        }
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadsPath);
    }
}
